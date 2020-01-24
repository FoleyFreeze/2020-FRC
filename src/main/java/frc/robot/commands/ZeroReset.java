package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ZeroReset extends CommandBase{

    private RobotContainer m_subsystem;

    public ZeroReset(RobotContainer subsystem){
        m_subsystem = subsystem;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_drivetrain.navX.zeroYaw();
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}