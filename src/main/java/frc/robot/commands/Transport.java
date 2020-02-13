package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Transport extends CommandBase{

    private RobotContainer m_subsystem;

    public Transport(RobotContainer subsystem){
        m_subsystem = subsystem;
    }

    @Override
    public void initialize(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_transporter.index(-1);
        } else{
            m_subsystem.m_transporter.index(1);
        }
    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}