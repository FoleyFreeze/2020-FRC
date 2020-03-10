package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.Intake;

public class ManualIntake extends CommandBase{

    private Intake mIntake;
    private Inputs mInput;

    public ManualIntake(Intake intake, Inputs input){
        mIntake = intake;
        mInput = input;
    }

    @Override
    public void initialize(){
        mIntake.dropIntake(true);
    }

    @Override
    public void execute(){
        if(mInput.shift()){
            mIntake.setPower(mIntake.k.backwardPower);
        } else{
            mIntake.setPower(mIntake.k.forwardPower);
        }
    }

    @Override
    public void end(boolean interrupted){
        mIntake.setPower(0);

        mIntake.dropIntake(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}