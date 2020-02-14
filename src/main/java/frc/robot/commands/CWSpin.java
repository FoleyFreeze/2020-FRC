package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class CWSpin extends CommandBase{

    private RobotContainer m_subsystem;

    public CWSpin(RobotContainer subsystem){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_transporterCW);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_transporterCW.deploy(true);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_transporterCW.deploy(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}