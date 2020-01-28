package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.PneumaticsCals;
import edu.wpi.first.wpilibj.Timer;

public class Pneumatics extends SubsystemBase{
    
    private Compressor mCompressor = new Compressor();
    private PneumaticsCals mCals;
    private double pauseTime;
    private boolean paused;
    public AnalogInput sensor = new AnalogInput(-1);
    public double pressure;

    public Pneumatics(PneumaticsCals cals){
        mCals = cals;
        if(mCals.disabled) return;
    }

    public void pauseReq(boolean breakThresh){
        if(mCals.disabled) return;
        if(breakThresh){
            paused = true;
            pauseTime = Timer.getFPGATimestamp() + mCals.pauseTime;
        }
    }

    @Override
    public void periodic(){
        if(mCals.disabled) return;
        if(Timer.getFPGATimestamp() > pauseTime){
            paused = false;
        }

        pressure = sensor.getVoltage() * 50 - 25;

        if(paused && pressure > mCals.minPressure){
            mCompressor.stop();
        } else mCompressor.start();
    }
}