package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class JoystickDrive extends CommandBase{
    private Drivetrain m_subsystem;
    private Joystick m_joy;

    public JoystickDrive(Drivetrain subsystem, Joystick joy){
        m_subsystem = subsystem;
        addRequirements(m_subsystem);

        m_joy = joy;
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public void initialize() {
        
    }

    @Override
    public void execute(){
        m_subsystem.drive(deadband(m_joy.getRawAxis(0)), -deadband(m_joy.getRawAxis(1)), deadband(m_joy.getRawAxis(4)), 0, 0, 0.5);
    }

    public double deadband(double val){
        if(Math.abs(val) < 0.05) val = 0;
        return val;
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}