package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class TrenchRun extends CommandBase{

    private RobotContainer m_subsystem;
    private double x;//create PID cal
    private Vector forward;
            
    public TrenchRun(RobotContainer subsystem){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_drivetrain.drive(forward = Vector.fromXY(x, m_subsystem.m_input.getY()),
        Math.PI / 2 - 
        Math.toRadians(m_subsystem.m_drivetrain.navX.getAngle()), 
        0, 0, m_subsystem.m_input.fieldOrient());
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}