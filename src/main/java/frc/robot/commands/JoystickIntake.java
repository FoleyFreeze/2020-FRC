package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class JoystickIntake extends CommandBase {

    private Intake m_subsystem;
    private double m_power;

    public JoystickIntake(Intake subsystem, double power){
        m_subsystem = subsystem;
        addRequirements(subsystem);

        m_power = power;
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        m_subsystem.setPower(m_power);
    }

    @Override
    public void end(boolean interrupted){

    }
    @Override
    public boolean isFinished(){
        return false;
    }
}