package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;

public class Jog extends CommandBase{

    CannonClimber mCannon;
    boolean jogUpDnvsLR;
    boolean jogPlusMinus;

    public Jog(CannonClimber cannon, boolean jogUpDnvsLR, boolean jogPlusMinus){
        mCannon = cannon;
        this.jogUpDnvsLR = jogUpDnvsLR;
        this.jogPlusMinus = jogPlusMinus;
    }

    @Override
    public void initialize(){
        if(jogUpDnvsLR){
            mCannon.jogLR(jogPlusMinus);
        } else {
            mCannon.jogUpDn(jogPlusMinus);
        }
    }

    @Override
    public void execute(){
        
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}