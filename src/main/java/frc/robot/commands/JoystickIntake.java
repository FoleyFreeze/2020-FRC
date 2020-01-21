package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class JoystickIntake extends CommandBase {

    private RobotContainer m_subsystem;
    private double m_power;

    public JoystickIntake(RobotContainer subsystem, double power){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_intake);

        m_power = power;
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        m_subsystem.m_intake.setPower(m_power);
    }

    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}