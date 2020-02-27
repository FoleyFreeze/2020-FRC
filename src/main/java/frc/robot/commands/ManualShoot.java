package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ManualShoot extends CommandBase{

    private RobotContainer m_subsystem;

    public ManualShoot(RobotContainer subsystem){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_cannonClimber);
        addRequirements(m_subsystem.m_transporterCW);
    }

    @Override
    public void initialize(){
        //m_subsystem.m_transporterCW.enablefire(true);
    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_cannonClimber.setpower(m_subsystem.m_cannonClimber.shootCals.manualPower * -1.0);
        } else{
            //m_subsystem.m_cannonClimber.setpower(m_subsystem.m_cannonClimber.shootCals.manualPower);
            m_subsystem.m_cannonClimber.prime(36);
            if(m_subsystem.m_cannonClimber.ready()){
                m_subsystem.m_transporterCW.enablefire(true);
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_cannonClimber.setpower(0);
        m_subsystem.m_transporterCW.enablefire(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}