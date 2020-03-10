package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeSpin extends CommandBase{
    
    private Intake mIntake;
    
    public IntakeSpin(Intake intake){
        mIntake = intake;
        addRequirements(mIntake);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        mIntake.setPower(mIntake.k.forwardPower);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}