package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TransporterCW;

public class CWStop extends CommandBase{

    TransporterCW mColorWheel;
    double stopTime;

    public CWStop(TransporterCW colorwheel){
        mColorWheel = colorwheel;
        addRequirements(mColorWheel);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        //transCW.(0.0); //TODO: figure out how to pass this command
        if(mColorWheel.detectedColor != mColorWheel.lastColor) stopTime = Timer.getFPGATimestamp();
    }

    @Override
    public void end(boolean interrupted){
        
    }

    @Override
    public boolean isFinished(){
        return mColorWheel.detectedColor == mColorWheel.lastColor && 
            Timer.getFPGATimestamp() >= stopTime+mColorWheel.k.stopBuffer;
    }
}