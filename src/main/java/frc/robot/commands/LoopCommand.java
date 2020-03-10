package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class LoopCommand extends CommandBase{
    protected final SequentialCommandGroup m_command;

  
    public LoopCommand(Command... commands) {
        m_command = new SequentialCommandGroup(commands);
        m_requirements.addAll(m_command.getRequirements());
    }

    @Override
    public void initialize() {
        m_command.initialize();
    }

    @Override
    public void execute() {
        m_command.execute();
    }

    @Override
    public void end(boolean interrupted) {
        m_command.end(interrupted);
    }

    @Override
    public boolean isFinished() {
        if(m_command.isFinished()){
            m_command.end(false);
            m_command.initialize();
        }
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return m_command.runsWhenDisabled();
    }
}