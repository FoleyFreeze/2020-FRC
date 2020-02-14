package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ManualIntake extends CommandBase{

    private RobotContainer m_subsystem;

    public ManualIntake(RobotContainer subsystem){
        m_subsystem = subsystem;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.shift()){
            m_subsystem.m_intake.setPower(m_subsystem.m_intake.mCals.backwardPower);
        } else{
            m_subsystem.m_intake.setPower(m_subsystem.m_intake.mCals.forwardPower);
        }

        m_subsystem.m_intake.dropIntake(true);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_intake.setPower(0);

        m_subsystem.m_intake.dropIntake(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}