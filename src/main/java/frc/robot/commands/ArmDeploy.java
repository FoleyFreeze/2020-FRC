package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IntakeArm;

public class ArmDeploy extends CommandBase{

    public IntakeArm m_Arm;
    public boolean ballsFull;

    public ArmDeploy(IntakeArm arm, boolean ballsFull){
        m_Arm = arm;
        addRequirements(m_Arm);
        this.ballsFull = ballsFull;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(ballsFull) m_Arm.deploy(false);
        else m_Arm.deploy(true);
    }

    @Override
    public void end(boolean interrupted){
        m_Arm.deploy(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}