package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Revolver;

public class TransIndex extends CommandBase{

    private Revolver mRevolver;
    private int idxCount;

    public TransIndex(Revolver revolver, int idxcount){
        mRevolver = revolver;
        idxCount = idxcount;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        mRevolver.index(idxCount);
    }

    @Override
    public void end(boolean interuppted){

    }

    @Override
    public boolean isFinished(){
        return Math.abs(mRevolver.tgtInTicks - mRevolver.currPos) < mRevolver.k.errorRange;
    }
}