package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.TransporterCW;

public class CWSpin extends CommandBase{

    private TransporterCW mColorWheel;
    private Inputs mInput;
    private int wedgeCount;
    private char gameData;

    public CWSpin(TransporterCW colorwheel, Inputs input){
        mColorWheel = colorwheel;
        mInput = input;
        addRequirements(mColorWheel);
        wedgeCount = 0;
        gameData = DriverStation.getInstance().getGameSpecificMessage().charAt(0);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        if(mInput.cwRotNotPos()){
            //transCW.loadMotor.setSpeed(m_cals.rotSpeed);
            if(mColorWheel.detectedColor != mColorWheel.lastColor) wedgeCount++;
        }else{
            //transCW.loadMotor.setSpeed(m_cals.colSpeed);
        }
    }

    @Override
    public void end(boolean interrupted){
    }

    @Override
    public boolean isFinished(){
        if(mInput.cwRotNotPos()){
            return wedgeCount >= 24;
        }else{
            switch(gameData){       //TODO fix me!!!
                case 'R':
                return mColorWheel.detectedColor == mColorWheel.k.Red;

                case 'G':
                return mColorWheel.detectedColor == mColorWheel.k.Green;

                case 'B':
                return mColorWheel.detectedColor == mColorWheel.k.Blue;

                case 'Y':
                return mColorWheel.detectedColor == mColorWheel.k.Yellow;

                default:
                return false;
            }
        }
    }
}