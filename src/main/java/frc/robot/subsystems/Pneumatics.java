package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.PneumaticsCals;

public class Pneumatics extends SubsystemBase{
    
    private Compressor mCompressor;
    private PneumaticsCals mCals;

    public Pneumatics(PneumaticsCals cals, Compressor compressor){
        mCals = cals;
        mCompressor = compressor;
    }

    public void startCompressor(boolean ready){
        if(ready){
            mCompressor.start();
        } else{
            mCompressor.stop();
        }
    }
}