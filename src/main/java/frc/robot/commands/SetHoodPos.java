package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.cals.HoodCals;
import frc.robot.subsystems.Hood;

public class SetHoodPos extends CommandBase{

    private Hood mHood;
    private HoodCals k;

    public SetHoodPos(Hood hood, HoodCals k){
        if(k.disabled) return;
        mHood = hood;
        this.k = k;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(mHood.tgtPos == mHood.currPos) return;
    }

    @Override
    public void end(boolean interrupted){

    }

    /*
    @Override
    public boolean isFinished(){
        if()
    }
    */
}