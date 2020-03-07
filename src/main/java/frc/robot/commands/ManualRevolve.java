package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.TransporterCW;

public class ManualRevolve extends CommandBase{

    private TransporterCW mTransporter;
    private Inputs mInput;

    public ManualRevolve(TransporterCW transporter, Inputs input){
        mTransporter = transporter;
        mInput = input;
    }

    @Override
    public void initialize(){
        if(mInput.shift()){
            mTransporter.index(-1);
        } else{
            mTransporter.index(1);
        }
    }

    @Override
    public void execute(){

    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}