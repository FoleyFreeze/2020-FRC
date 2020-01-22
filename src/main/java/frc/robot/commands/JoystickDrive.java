package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;

public class JoystickDrive extends CommandBase{
    private Drivetrain m_subsystem;
    private Inputs m_inputs;

    public JoystickDrive(Drivetrain subsystem, Inputs inputs){
        m_subsystem = subsystem;
        addRequirements(m_subsystem);

        m_inputs = inputs;
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
        m_subsystem.drive(m_inputs.getXY(), m_inputs.getRot(), 0, 0, m_inputs.fieldOrient());
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}