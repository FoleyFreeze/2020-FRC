package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Shoot extends CommandBase{

    private RobotContainer m_subsystem;
    private double m_power;

    public Shoot(RobotContainer subsystem, double power){
        m_subsystem = subsystem;

        m_power = power;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_cannonClimber.setpower(-m_power);
        } else{
            m_subsystem.m_cannonClimber.setpower(m_power);
        }
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_cannonClimber.setpower(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}