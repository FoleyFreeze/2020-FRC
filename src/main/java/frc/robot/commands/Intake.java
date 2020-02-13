package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class Intake extends CommandBase{

    private RobotContainer m_subsystem;
    private double m_power;
    private boolean m_activated;

    public Intake(RobotContainer subsystem, double power, boolean activated){
        m_subsystem = subsystem;

        m_power = power;
        m_activated = activated;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_intake.setPower(-m_power);
        } else{
            m_subsystem.m_intake.setPower(m_power);
        }

        m_subsystem.m_intake.dropIntake(m_activated);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_intake.setPower(0);

        m_subsystem.m_intake.dropIntake(!m_activated);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}