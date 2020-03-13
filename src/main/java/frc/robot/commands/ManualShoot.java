package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.Revolver;
import frc.robot.subsystems.Hood.HoodPos;

public class ManualShoot extends CommandBase{

    private CannonClimber mCannon;
    private Revolver mRevolver;
    private Hood mHood;
    private Inputs mInput;

    public ManualShoot(CannonClimber cannon, Revolver revolver, Hood hood, Inputs input){
        mCannon = cannon;
        mRevolver = revolver;
        mHood = hood;
        mInput = input;
    }

    @Override
    public void initialize(){
        //m_subsystem.m_transporterCW.enablefire(true);
    }

    @Override
    public void execute(){
        if(mInput.shift()){
            mCannon.setpower(mCannon.k.manualPower * -1.0);
        } else{
            //m_subsystem.m_cannonClimber.setpower(m_subsystem.m_cannonClimber.shootCals.manualPower);
            /*m_subsystem.m_cannonClimber.prime(36);
            if(m_subsystem.m_cannonClimber.ready()){
                m_subsystem.m_transporterCW.enablefire(true);
            }*/
            mCannon.setspeed(mCannon.k.initJogDist * 100);
            mRevolver.enablefire(true);
            int jogAng = (int) mCannon.k.initJogAng;
            if(jogAng == 0){
                mHood.tgtPos = HoodPos.LOW;
            } else if(jogAng == 1){
                mHood.tgtPos = HoodPos.MID1;
            } else if(jogAng == 2){
                mHood.tgtPos = HoodPos.MID2;
            } else if(jogAng == 3){
                mHood.tgtPos = HoodPos.HIGH;
            }
        }
    }

    @Override
    public void end(boolean interrupted){
        mCannon.setpower(0);
        mRevolver.enablefire(false);
        mHood.tgtPos = HoodPos.LOW;
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}