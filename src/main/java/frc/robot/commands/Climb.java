package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;

public class Climb extends CommandBase{

    public double m_power;
    private CannonClimber mClimber;

    public Climb(CannonClimber climber, double power){
        mClimber = climber;
        addRequirements(mClimber);

        m_power = power;
    }

    @Override
    public void initialize(){
        mClimber.climbMode(true);
    }

    @Override
    public void execute(){
        mClimber.setpower(m_power);
    }

    @Override
    public void end(boolean interrupted){
        mClimber.setpower(0);
        mClimber.climbMode(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}