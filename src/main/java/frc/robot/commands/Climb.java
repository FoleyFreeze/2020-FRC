package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RobotState;
import frc.robot.util.Vector;

public class Climb extends CommandBase{

    public double m_power;
    private CannonClimber mClimber;
    private Drivetrain mDrivetrain;
    private RobotState mState;//MSU

    public Climb(CannonClimber climber, double power, Drivetrain drivetrain, RobotState state){
        mClimber = climber;
        mDrivetrain = drivetrain;
        mState = state;
        addRequirements(mClimber);
        addRequirements(mDrivetrain);

        m_power = power;
    }

    @Override
    public void initialize(){
        mClimber.climbMode(true);
        mClimber.climbBrake(mClimber.climbLatchOn);
    }

    @Override
    public void execute(){
        if(mState.robotAng <= mDrivetrain.k.climberAngle + mDrivetrain.k.climberAllowableErr 
          && mState.robotAng >= mDrivetrain.k.climberAngle - mDrivetrain.k.climberAllowableErr){
            mDrivetrain.drive(Vector.fromXY(0, 0), mDrivetrain.k.climberRotSpeed);
        }
        mClimber.setpower(m_power);
    }

    @Override
    public void end(boolean interrupted){
        mClimber.climbLatchOn = true;
        mClimber.climbBrake(mClimber.climbLatchOn);
        mClimber.setpower(0);
        mClimber.climbMode(true);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}