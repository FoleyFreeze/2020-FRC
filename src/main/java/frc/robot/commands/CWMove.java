package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GateCW;
import frc.robot.subsystems.RobotState;
import frc.robot.util.Vector;

public class CWMove extends CommandBase{
    
    GateCW mColorWheel;
    Drivetrain mDrivetrain;
    RobotState mState;
    Vector xy;
    double start;

    public CWMove(GateCW colorwheel, Drivetrain drivetrain, RobotState state){
        mColorWheel = colorwheel;
        mState = state;
        addRequirements(mColorWheel);
        xy = Vector.fromXY(0, -1);
        start = mState.getYPos();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        mDrivetrain.drive(xy, 0);
    }

    @Override
    public void end(boolean interrupted){
        mColorWheel.deployCW(false);
    }

    @Override
    public boolean isFinished(){
        return mState.getYPos() >= start - 3;
    }
}