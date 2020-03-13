package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GateCW;

public class CWInit extends CommandBase{
    
    GateCW mColorWheel;

    public CWInit(GateCW colorwheel){
        mColorWheel = colorwheel;
        addRequirements(colorwheel);
    }

    @Override
    public void initialize(){
        mColorWheel.CWNotTransport.set(true);
    }

    @Override
    public void execute(){
        //transCW.loadMotor.setSpeed(m_cals.cwInitSpeed);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return (mColorWheel.detectedColor == mColorWheel.k.Red || 
            mColorWheel.detectedColor == mColorWheel.k.Blue || mColorWheel.detectedColor 
            == mColorWheel.k.Green || mColorWheel.detectedColor == mColorWheel.k.Yellow);
    }
}