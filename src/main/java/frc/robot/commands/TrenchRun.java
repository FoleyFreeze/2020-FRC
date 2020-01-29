package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class TrenchRun extends CommandBase{

    private RobotContainer m_subsystem;
    private double x;//create PID cal
    private Vector forward;
    private double wallDist;
    public enum Orientation{
        GATHERER, CLIMBER, AUTO
    }

    public TrenchRun(RobotContainer subsystem){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        switch(Orientation){
            case GATHERER:
                forward = Vector.fromXY(/*lidarsensor-*/m_subsystem.m_drivetrain.k.gathererDist, m_subsystem.m_input.getY());
                break;
            case CLIMBER:
                forward = Vector.fromXY(/*lidarsensor-*/m_subsystem.m_drivetrain.k.climberDist, m_subsystem.m_input.getY());
                break;
            case AUTO:
                if(180 - Math.abs(m_subsystem.m_drivetrain.navX.getAngle()) < 90 - Math.abs(m_subsystem.m_drivetrain.navX.getAngle()))
        }
        m_subsystem.m_drivetrain.drive();
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}