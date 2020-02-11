package frc.robot.subsystems;

import java.sql.Time;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.CannonCals;
import frc.robot.motors.Motor;
import frc.robot.util.Util;

public class Cannon extends SubsystemBase{

    public CannonCals mCals;
    private Motor motor;
    public enum HoodPos{
        LOW, MID1, MID2, HIGH
    }
    private Solenoid hoodSol;
    private Solenoid stopSol;

    HoodPos hCurrPos = HoodPos.LOW;
    HoodPos hTgtPos = HoodPos.LOW;
    double solRestTime = 0;

    public Cannon(CannonCals cals){
        mCals = cals;
        if(cals.disabled) return;

        motor = Motor.initMotor(mCals.cannonMotor);
    }
    
    public void setpower(double power){
        if(mCals.disabled) return;
        motor.setPower(power);
    }

    public void setspeed(double speed){
        if(mCals.disabled) return;
        motor.setSpeed(speed);
    }

    public void prime(double distToTgt){
        double[] distAxis = mCals.dist[hTgtPos.ordinal()];
        if(distAxis[0] > distToTgt){
            switch(hTgtPos){
                case LOW:
                    hTgtPos = HoodPos.LOW;
                    break;
                
                case MID1:
                    hTgtPos = HoodPos.LOW;
                    break;

                case MID2:
                    hTgtPos = HoodPos.MID1;
                    break;

                case HIGH:
                    hTgtPos = HoodPos.MID2;
                    break;
            }
        }

        if(distAxis[distAxis.length - 1] < distToTgt){
            switch(hTgtPos){
                case LOW:
                    hTgtPos = HoodPos.MID1;
                    break;

                case MID1:
                    hTgtPos = HoodPos.MID2;
                    break;

                case MID2:
                    hTgtPos = HoodPos.HIGH;
                    break;
                    
                case HIGH:
                    hTgtPos = HoodPos.HIGH;
                    break;
            }
        }

        double speed = Util.interpolate(mCals.rpm[hTgtPos.ordinal()], mCals.dist[hTgtPos.ordinal()], distToTgt);
        setspeed(speed);
    }

    public boolean ready(){
        return false;
    }

    public void periodic(){
        if(Timer.getFPGATimestamp()>solRestTime){
            switch(hTgtPos){
                case LOW:
                    if(hCurrPos != HoodPos.LOW){
                        hoodSol.set(false);
                        stopSol.set(false);
                        hCurrPos = HoodPos.LOW;
                        solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                    }
                    break;

                case MID1:
                    if(hCurrPos != HoodPos.MID1){
                        if(hCurrPos == HoodPos.LOW){
                            hoodSol.set(true);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID1;
                            solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                        }else{
                            hoodSol.set(false);
                            stopSol.set(false);
                            hCurrPos = HoodPos.LOW;
                            solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                        }
                    }
                    break;

                case MID2:
                    if(hCurrPos != HoodPos.MID2){
                        if(hCurrPos == HoodPos.HIGH){
                            hoodSol.set(false);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID2;
                            solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                        }else{
                            hoodSol.set(true);
                            stopSol.set(false);
                            hCurrPos = HoodPos.HIGH;
                            solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                        }
                    }
                    break;

                case HIGH:
                    if(hCurrPos != HoodPos.HIGH){
                        hoodSol.set(true);
                        stopSol.set(false);
                        hCurrPos = HoodPos.HIGH;
                        solRestTime = Timer.getFPGATimestamp() + mCals.SOL_RESTTIME;
                    }
                    break;
            }
        }
        
    }
}