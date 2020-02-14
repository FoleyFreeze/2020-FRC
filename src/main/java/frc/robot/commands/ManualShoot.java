package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ManualShoot extends CommandBase{

    private RobotContainer m_subsystem;

    public ManualShoot(RobotContainer subsystem){
        m_subsystem = subsystem;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_cannonClimber.setpower(m_subsystem.m_cannonClimber.shootCals.power);
        } else{
            m_subsystem.m_cannonClimber.setpower(m_subsystem.m_cannonClimber.shootCals.power * -1.0);
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