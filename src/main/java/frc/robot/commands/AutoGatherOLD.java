package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GateCW;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Vision;
import frc.robot.util.Vector;

public class AutoGatherOLD extends CommandBase {

    private Intake mIntake;
    private Drivetrain mDrivetrain;
    private Vision mVision;
    private GateCW mTransporter;
    private Inputs mInput;
    private RobotState mState;
    private boolean auton;

    public AutoGatherOLD(Intake intake, Drivetrain drivetrain, Vision vision, GateCW transporter, Inputs input, RobotState state){
        mIntake = intake;
        mDrivetrain = drivetrain;
        mVision = vision;
        mTransporter = transporter;
        mInput = input;
        mState = state;
        addRequirements(mIntake);
        addRequirements(mDrivetrain);
    }

    @Override
    public void initialize(){
        auton = DriverStation.getInstance().isAutonomous();
        mVision.NTEnablePiBall(true);
        mIntake.dropIntake(true);
    }

    @Override
    public void execute(){
        if(mInput.enableBallCam() && mVision.hasBallImage()){//robot has control

            Vector strafe = Vector.fromXY(0, mVision.ballData.element().dist * 
                mDrivetrain.k.trenchRunDistKp);

            double rot = mVision.ballData.element().angle -
                 mDrivetrain.navX.getAngle() * mDrivetrain.k.trenchRunAngKp;

            mDrivetrain.drive(strafe, rot, 0, 0, mInput.fieldOrient());
        }else{//driver has control
            Vector strafe = Vector.fromXY(mInput.getX(), mInput.getY());

            double rot = mInput.getRot();

            mDrivetrain.drive(strafe, rot, 0, 0, mInput.fieldOrient());
        }
        
        if(mState.ballnumber >= 5 && !mInput.shift()){
            mIntake.dropIntake(false);
            mIntake.setPower(mIntake.k.backwardPower);
        } else if(mTransporter.isIndexing()){
            mIntake.setPower(mIntake.k.idxPower);
        } else {
            mIntake.setPower(mIntake.k.forwardPower);
        }
    }

    @Override
    public void end(boolean interrupted){
        mIntake.dropIntake(false);
        mIntake.setPower(0);
        mVision.NTEnablePiBall(false);
    }
    
    @Override
    public boolean isFinished(){
        if(auton){
            return mState.ballnumber >= 5;
        }
        return false;
    }
}