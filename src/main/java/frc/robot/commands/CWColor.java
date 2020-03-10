package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TransporterCW;
import edu.wpi.first.wpilibj.util.Color;

public class CWColor extends CommandBase{

    TransporterCW mCW;
    char tgtColor = mCW.gameData.charAt(0);
    Color detectedColor = mCW.detectedColor;

    public CWColor(TransporterCW cw){
        mCW = cw;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        mCW.deployCW(true);
    }

    @Override
    public void end(boolean interrupted){
        mCW.deployCW(false);
    }

    @Override
    public boolean isFinished(){
        
        switch(tgtColor){
            case 'B':
            return detectedColor == mCW.k.Red;//We see red, but it the detector sees blue

            case 'G':
            return detectedColor == mCW.k.Yellow;//We see yellow, but it the detector sees green

            case 'R':
            return detectedColor == mCW.k.Blue;//We see blue, but it the detector sees red

            case 'Y':
            return detectedColor == mCW.k.Green;//We see green, but it the detector sees yellow

            default:
            return false;
        }
    }
}