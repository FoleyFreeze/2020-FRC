package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Inputs;

public class TrenchRun extends CommandBase{

    private RobotContainer m_subsystem;

    public TrenchRun(RobotContainer y){
        this.m_subsystem = y;
        addRequirements(m_subsystem.m_drivetrain);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}