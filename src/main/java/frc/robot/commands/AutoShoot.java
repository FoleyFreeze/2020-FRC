package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.TransporterCW;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.Vision.VisionData;

public class AutoShoot extends CommandBase{

    private Drivetrain mDrivetrain;
    private CannonClimber mCannon;
    private TransporterCW mTransporter;
    private Inputs mInput;
    private Vision mVision;
    private RobotState mState;
    public double rotCam = 0.0;
    public boolean auton;
    public double shootFinTime;
    double prevRobotAngle;
    double prevTime;
    
    public AutoShoot(Drivetrain drivetrain, CannonClimber cannon, TransporterCW transporter, Inputs input, Vision vision, RobotState state){
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

            dist = image.dist;

            if(mInput.twoVThree()){
                double angOfTgt = 180 - rotCam;
                dist = Math.sqrt(dist*dist + 29.25*29.25 - 2 * dist * 29.25 * Math.cos(angOfTgt));

                rotCam = Math.asin(29.25/dist * Math.sin(angOfTgt));
            }

            double robotAngle = mDrivetrain.robotAng;
            error = rotCam - robotAngle;
            error %= 360;
            if(error > 180) error -=360;
            else if(error < -180) error +=360;
            
            double deltaAngle = robotAngle - prevRobotAngle;
            deltaAngle %= 360;
            if(deltaAngle > 180) deltaAngle -=360;
            else if(deltaAngle < -180) deltaAngle +=360;

            double d = (deltaAngle)/(time - prevTime);
            rot = error * mCannon.k.kPDrive - d * mCannon.k.kDDrive;

            if(rot > mCannon.k.maxRot) rot = mCannon.k.maxRot;
            else if(rot < -mCannon.k.maxRot) rot = -mCannon.k.maxRot;

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

        if(mCannon.ready() && aligned && mState.ballnumber > 0){
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