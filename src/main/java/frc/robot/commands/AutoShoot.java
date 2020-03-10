package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GateCW;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionData;
import frc.robot.util.Util;

public class AutoShoot extends CommandBase{

    private Drivetrain mDrivetrain;
    private CannonClimber mCannon;
    private GateCW mTransporter;
    private Inputs mInput;
    private Vision mVision;
    private RobotState mState;
    public double rotCam = 0.0;
    public boolean auton;
    public double shootFinTime;
    double prevRobotAngle;
    double prevTime;
    
    public AutoShoot(Drivetrain drivetrain, CannonClimber cannon, GateCW transporter, Inputs input, Vision vision, RobotState state){
        mDrivetrain = drivetrain;
        mCannon = cannon;
        mTransporter = transporter;
        mInput = input;
        mVision = vision;
        mState = state;

        addRequirements(mDrivetrain);
        addRequirements(mCannon);
        addRequirements(mTransporter);
    }

    @Override
    public void initialize(){
        if(mInput.cam()){
            mCannon.setCamLights(true);
            mVision.NTEnablePiTgt(true);
        }
        
        auton = DriverStation.getInstance().isAutonomous();
        prevRobotAngle = mDrivetrain.robotAng;
        prevTime = 0;
    }

    @Override
    public void execute(){
        double time = Timer.getFPGATimestamp();
        double rot;
        double error;
        boolean aligned = false;
        double dist;
        if(mVision.hasTargetImage() && mInput.cam()){
            VisionData image = mVision.targetData.getFirst();
            rotCam = image.robotangle + image.angle + mCannon.k.initJogAng;

            double camAng = image.angle + mCannon.k.initJogAng;
            double camAngError = Util.angleDiff(mDrivetrain.robotAng, camAng);
            double angDelta = Util.angleDiff(image.robotangle, mDrivetrain.robotAng);
            rotCam = Util.angleDiff(camAngError, angDelta);
            error = rotCam;
            dist = image.dist;

            //if we are doing 3 pointers
            if(mInput.twoVThree()){
                double angTgt = rotCam - mDrivetrain.robotAng;
                double hypSin = dist * Math.sin(angTgt);
                double hypCos = dist * Math.cos(angTgt) + 29.25;

                dist = Math.sqrt(hypSin*hypSin + hypCos*hypCos);
                error = Math.atan(hypSin/hypCos);
            }

            double robotAngle = mDrivetrain.robotAng;
            error = rotCam - robotAngle;
            error %= 360;
            if(error > 180) error -=360;
            else if(error < -180) error +=360;
            
            double deltaAngle = Util.angleDiff(robotAngle, prevRobotAngle);

            double d = (deltaAngle)/(time - prevTime);
            rot = error * mCannon.k.kPDrive - d * mCannon.k.kDDrive;

            if(rot > mCannon.k.maxRot) rot = mCannon.k.maxRot;
            else if(rot < -mCannon.k.maxRot) rot = -mCannon.k.maxRot;

        } else if(DriverStation.getInstance().isAutonomous()){
            rot = 0;
            error = 0;
            dist = mCannon.k.autonDist;
        }else {
            rot = mInput.getRot();
            error = 0;
            if(mInput.layup()){
                dist = mCannon.k.layupDist;
            } else dist = mCannon.k.trenchDist;
        }
        if(Math.abs(error) <= mCannon.k.tolerance) aligned = true;//make dependent on dist
        
        mDrivetrain.drive(mInput.getXY(), rot, 0, 0, 
            mInput.fieldOrient());
        
        mCannon.prime(dist);

        if(/*mCannon.ready() &&*/ aligned && mState.ballnumber > 0){
            mTransporter.shootAll();
            shootFinTime = Timer.getFPGATimestamp() + mCannon.k.shootTime;
        } 
        else mTransporter.stoprot();

        prevRobotAngle = mDrivetrain.robotAng;
        prevTime = time;
    }

    @Override
    public void end(boolean interrupted){
        mCannon.setpower(0);
        mCannon.setCamLights(false);
        mVision.NTEnablePiTgt(false);
    }

    @Override
    public boolean isFinished(){
        if(auton) return Timer.getFPGATimestamp() >= shootFinTime && mState.ballnumber == 0;
        return false;
    }
}