package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.PneumaticsCals;
import edu.wpi.first.wpilibj.Timer;

public class Pneumatics extends SubsystemBase{
    
    private Compressor mCompressor;
    private PneumaticsCals k;
    private double pauseTime;
    private boolean paused;
    public AnalogInput pressureSensor;
    public double pressure;

    public Pneumatics(PneumaticsCals k){
        this.k = k;
        if(k.disabled) return;
        mCompressor = new Compressor();
        pressureSensor = new AnalogInput(k.PNE_PSENSORID);
    }

    public void pauseReq(boolean breakThresh){
        if(breakThresh){
            paused = true;
            pauseTime = Timer.getFPGATimestamp() + k.pauseTime;
        }
    }

    @Override
    public void periodic(){
        if(k.disabled) return;
        if(Timer.getFPGATimestamp() > pauseTime){
            paused = false;
        }

        pressure = pressureSensor.getVoltage() * 50 - 25;

        if(paused && pressure > k.minPressure){
            mCompressor.stop();
        } else if(pressure > k.maxPressure){
            mCompressor.stop();
        } else if(pressure < k.maxPressure - k.hystPressure){
            mCompressor.start();
        }

        Display.put("Pressure", pressure);
        Display.put("CompressorRun", mCompressor.getCompressorCurrent() > 0);
    }
}