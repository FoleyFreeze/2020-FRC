package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Climb extends CommandBase{

    public double m_power;
    private RobotContainer m_subsystem;

    public Climb(RobotContainer subsystem, double power){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_climber);

        m_power = power;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_climber.setPower(m_power);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}