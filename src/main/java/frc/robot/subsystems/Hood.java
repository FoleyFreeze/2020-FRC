package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.HoodCals;

public class Hood extends SubsystemBase{
    
    private Solenoid hoodSol;
    private Solenoid stopSol;
    public enum HoodPos{
        LOW, MID1, MID2, HIGH
    }
    public HoodPos currPos = HoodPos.LOW;
    public HoodPos tgtPos = HoodPos.LOW;
    
    public Hood(HoodCals k){
        if(k.disabled) return;
        hoodSol = new Solenoid(k.hoodSol);
        stopSol = new Solenoid(k.stopSol);
    }

    @Override
    public void periodic() {
        if(DriverStation.getInstance().isDisabled()){
            currPos = HoodPos.LOW;
            tgtPos = HoodPos.LOW;
        }
    }

    public void setHoodSol(boolean set){
        hoodSol.set(set);
    }

    public void setStopSol(boolean set){
        stopSol.set(set);
    }
}