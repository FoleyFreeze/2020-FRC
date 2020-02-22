package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.CannonCals;
import frc.robot.cals.ClimberCals;
import frc.robot.motors.Motor;
import frc.robot.util.Util;

public class CannonClimber extends SubsystemBase{

    public CannonCals shootCals;
    public ClimberCals climbCals;
    private Motor motor;
    private Motor motor2;
    public enum HoodPos{
        LOW, MID1, MID2, HIGH
    }
    private Solenoid hoodSol;
    private Solenoid stopSol;
    private Solenoid camLightsSol;

    private Solenoid shootVsClimb;

    private Solenoid dropFoot;

    HoodPos hCurrPos = HoodPos.LOW;
    HoodPos hTgtPos = HoodPos.LOW;
    double solRestTime = 0;

    public CannonClimber(CannonCals sCals, ClimberCals cCals){
        shootCals = sCals;
        climbCals = cCals;
        if(sCals.disabled || cCals.disabled) return;

        motor = Motor.initMotor(shootCals.ccMotor);
        motor2 = Motor.initMotor(shootCals.ccMotor2);

        hoodSol = new Solenoid(sCals.hoodSolValue);
        stopSol = new Solenoid(sCals.stopSolValue);
        camLightsSol = new Solenoid(sCals.camLightsSol);
        shootVsClimb = new Solenoid(sCals.ShootVClimbValue);
        dropFoot = new Solenoid(cCals.dropFootValue);
    }
    
    public void setpower(double power){
        if(shootCals.disabled) return;
        motor.setPower(power);
        motor2.setPower(power);
        Display.put("CCMotorCurrent 0", motor.getCurrent());
        Display.put("CCMotorCurrent 1", motor2.getCurrent());
        Display.put("CC Motor Temp 0", motor.getTemp());
        Display.put("CC Motor Temp 1", motor2.getTemp());
    }

    public void setspeed(double speed){
        if(shootCals.disabled) return;
        motor.setSpeed(speed);
        motor2.setSpeed(speed);
    }

    public void prime(double distToTgt){
        distToTgt += shootCals.initJogDist;
        if(shootCals.disabled) return;
        if(!climbCals.disabled) shootVsClimb.set(false);

        double[] distAxis = shootCals.dist[hTgtPos.ordinal()];
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

        double speed = Util.interpolate(shootCals.rpm[hTgtPos.ordinal()], shootCals.dist[hTgtPos.ordinal()], distToTgt);
        setspeed(speed);
    }

    public boolean ready(){
        return false;
    }

    public void periodic(){
        if(climbCals.disabled || shootCals.disabled) return;
        if(Timer.getFPGATimestamp()>solRestTime){
            switch(hTgtPos){
                case LOW:
                    if(hCurrPos != HoodPos.LOW){
                        hoodSol.set(false);
                        stopSol.set(false);
                        hCurrPos = HoodPos.LOW;
                        solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                    }
                    break;

                case MID1:
                    if(hCurrPos != HoodPos.MID1){
                        if(hCurrPos == HoodPos.LOW){
                            hoodSol.set(true);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID1;
                            solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                        }else{
                            hoodSol.set(false);
                            stopSol.set(false);
                            hCurrPos = HoodPos.LOW;
                            solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                        }
                    }
                    break;

                case MID2:
                    if(hCurrPos != HoodPos.MID2){
                        if(hCurrPos == HoodPos.HIGH){
                            hoodSol.set(false);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID2;
                            solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                        }else{
                            hoodSol.set(true);
                            stopSol.set(false);
                            hCurrPos = HoodPos.HIGH;
                            solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                        }
                    }
                    break;

                case HIGH:
                    if(hCurrPos != HoodPos.HIGH){
                        hoodSol.set(true);
                        stopSol.set(false);
                        hCurrPos = HoodPos.HIGH;
                        solRestTime = Timer.getFPGATimestamp() + shootCals.SOL_RESTTIME;
                    }
                    break;
            }
        }
        Display.put("Foot Dropped", dropFoot.get());
        Display.put("CCMotorCurrent 0", motor.getCurrent());
        Display.put("CCMotorCurrent 1", motor2.getCurrent());
        Display.put("CC Motor Temp 0", motor.getTemp());
        Display.put("CC Motor Temp 1", motor2.getTemp());
    }

    public void jogUpDn(boolean up){
        if(up){
            shootCals.initJogDist += shootCals.distJog;
        }else{
            shootCals.initJogDist -= shootCals.distJog;
        }
    }

    public void jogLR(boolean left){
        if(left){
            shootCals.initJogAng -= shootCals.angJog;
        }else{
            shootCals.initJogAng += shootCals.angJog;
        }
    }

    //Climber
    public void climbMode(){
        if(climbCals.disabled && shootCals.disabled) return;
        shootVsClimb.set(true);
    }
    public void dropFoot(boolean on){
        if(climbCals.disabled && shootCals.disabled) return;
        dropFoot.set(on);
    }
    public void setCamLights(boolean on){
        camLightsSol.set(on);
    }
}