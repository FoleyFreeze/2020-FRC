package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Inputs;

public class ManualShoot extends CommandBase{

    private CannonClimber mCannon;
    private Inputs mInput;

    public ManualShoot(CannonClimber cannon, Inputs input){
        mCannon = cannon;
        mInput = input;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(mInput.shift()){
            mCannon.setpower(mCannon.m_cannonClimber.shootCals.power * -1.0);
        } else{
            mCannon.setpower(mCannon.k.power);
        }
    }

    @Override
    public void end(boolean interrupted){
        mCannon.setpower(0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}