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
    public HoodCals k;
    
    public Hood(HoodCals k){
        this.k = k;
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

    public void moveHood(){
        switch(currPos){
            case LOW:
                switch(tgtPos){
                    case LOW:
                        currPos = HoodPos.LOW;
                        break;
                    case MID1:
                        currPos = HoodPos.MID1;
                        break;
                    case MID2:
                        currPos = HoodPos.HIGH;
                        break;
                    case HIGH:
                        currPos = HoodPos.HIGH;
                        break;
                }
                break;
            
            case MID1:
                switch(tgtPos){
                    case LOW:
                        currPos = HoodPos.LOW;
                        break;
                    case MID1:
                        currPos = HoodPos.MID1;
                        break;
                    case MID2:
                        currPos = HoodPos.HIGH;
                        break;
                    case HIGH:
                        currPos = HoodPos.HIGH;
                        break;
                }
                break;
            case MID2:
                switch(tgtPos){
                    case LOW:
                        currPos = HoodPos.LOW;
                        break;

                    case MID1:
                        currPos = HoodPos.LOW;
                        break;
                    case MID2:
                        currPos = HoodPos.MID2;
                        break;
                    case HIGH:
                        currPos = HoodPos.HIGH;
                        break;
                }
                break;
            case HIGH:
                switch(tgtPos){
                    case LOW:
                        currPos = HoodPos.LOW;
                        break;
                    case MID1:
                        currPos = HoodPos.LOW;
                        break;
                    case MID2:
                        currPos = HoodPos.MID2;
                        break;
                    case HIGH:
                        currPos = HoodPos.HIGH;
                        break;
                }
                break;
        }
        switch(currPos){
            case LOW:
                setHoodSol(false);
                setStopSol(false);
                break;
            case MID1:
                setStopSol(true);
                setHoodSol(true);
                break;
            case MID2:
                setStopSol(true);
                setHoodSol(false);
                break;
            case HIGH:
                setHoodSol(true);
                setHoodSol(false);
                break;
        }
    }

    public boolean checkPos(){
        return currPos != tgtPos;
    }
}