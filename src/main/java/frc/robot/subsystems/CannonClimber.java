package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.CannonClimbCals;
import frc.robot.motors.Motor;
import frc.robot.util.Util;

public class CannonClimber extends SubsystemBase{

    public CannonClimbCals k;
    private Motor motor;
    private Motor motor2;
    public enum HoodPos{
        LOW, MID1, MID2, HIGH
    }
    private Solenoid hoodSol;
    private Solenoid stopSol;
    private Solenoid camLightsSol;

    private Solenoid shootVsClimb;

    private Solenoid climbBrake;

    public boolean climbLatchOn = false;

    //private Solenoid dropFoot;

    HoodPos hCurrPos = HoodPos.LOW;
    public HoodPos hTgtPos = HoodPos.LOW;
    double solRestTime = 0;
    double targetSpeed = 0;

    public CannonClimber(CannonClimbCals k){
        this.k = k;
        if(k.shootDisabled || k.climbDisabled) return;

        motor = Motor.initMotor(k.ccMotor);
        motor2 = Motor.initMotor(k.ccMotor2);

        hoodSol = new Solenoid(k.hoodSolValue);
        stopSol = new Solenoid(k.stopSolValue);
        camLightsSol = new Solenoid(k.camLightsSol);
        shootVsClimb = new Solenoid(k.ShootVClimbValue);
        //dropFoot = new Solenoid(k.dropFootValue);
        climbBrake = new Solenoid(k.climbBrake);
    }
    
    public void setpower(double power){
        if(k.shootDisabled) return;
        motor.setPower(power);
        //motor2.setPower(power);
        Display.put("CCMotorCurrent 0", motor.getCurrent());
        Display.put("CCMotorCurrent 1", motor2.getCurrent());
        Display.put("CC Motor Temp 0", motor.getTemp());
        Display.put("CC Motor Temp 1", motor2.getTemp());
        
        targetSpeed = 0;
    }

    public void setspeed(double speed){
        if(k.shootDisabled) return;
        motor.setSpeed(speed);
        motor2.setSpeed(speed);
    }

    /*public void prime(double distToTgt){
        distToTgt += k.initJogDist;
        if(k.shootDisabled) return;
        if(!k.shootDisabled) shootVsClimb.set(false);

        double[] distAxis = k.dist[hTgtPos.ordinal()];


        if(k.shootDisabled) return;
        //motor.setSpeed(speed);
        //motor2.setSpeed(speed);
        motor.setPower(speed / k.falconRpmPerPower);
        targetSpeed = speed;
    }*/

    public void prime(double distToTgt){
        distToTgt += k.initJogDist;
        if(k.shootDisabled) return;
        if(!k.shootDisabled) shootVsClimb.set(false);
        
        double[] distAxis = k.dist[hTgtPos.ordinal()];
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
    

        double speed = Util.interpolate(k.rpm[hTgtPos.ordinal()], k.dist[hTgtPos.ordinal()], distToTgt);
        setspeed(speed);
    }

    /*public boolean ready(){
        if(subsystem.m_transporterCW.launcher.get()){
            return Math.abs(motor.getSpeed() - targetSpeed) < k.allowedRpmHyst;
        } else {
            return Math.abs(motor.getSpeed() - targetSpeed) < k.allowedRpmError;
        }
    }*/

    public void periodic(){
        if(DriverStation.getInstance().isDisabled()){
            hTgtPos = HoodPos.LOW;
        }

        if(DriverStation.getInstance().isAutonomous()){
            hTgtPos = HoodPos.HIGH;
        }

        if(k.shootDisabled || k.climbDisabled) return;
        if(Timer.getFPGATimestamp()>solRestTime){
            switch(hTgtPos){
                case LOW:
                    if(hCurrPos != HoodPos.LOW){
                        hoodSol.set(false);
                        stopSol.set(false);
                        hCurrPos = HoodPos.LOW;
                        solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                    }
                    break;

                case MID1:
                    if(hCurrPos != HoodPos.MID1){
                        if(hCurrPos == HoodPos.LOW){
                            hoodSol.set(true);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID1;
                            solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                        }else{
                            hoodSol.set(false);
                            stopSol.set(false);
                            hCurrPos = HoodPos.LOW;
                            solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                        }
                    }
                    break;

                case MID2:
                    if(hCurrPos != HoodPos.MID2){
                        if(hCurrPos == HoodPos.HIGH){
                            hoodSol.set(false);
                            stopSol.set(true);
                            hCurrPos = HoodPos.MID2;
                            solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                        }else{
                            hoodSol.set(true);
                            stopSol.set(false);
                            hCurrPos = HoodPos.HIGH;
                            solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                        }
                    }
                    break;

                case HIGH:
                    if(hCurrPos != HoodPos.HIGH){
                        hoodSol.set(true);
                        stopSol.set(false);
                        hCurrPos = HoodPos.HIGH;
                        solRestTime = Timer.getFPGATimestamp() + k.SOL_RESTTIME;
                    }
                    break;
            }
        }
        //Display.put("Foot Dropped", dropFoot.get());
        Display.put("CCMotorCurrent 0", motor.getCurrent());
        Display.put("CCMotorCurrent 1", motor2.getCurrent());
        Display.put("CC Motor Temp 0", motor.getTemp());
        Display.put("CC Motor Temp 1", motor2.getTemp());
        Display.put("RPM", motor.getSpeed());
        Display.put("Hood Pos", hCurrPos.toString());
    }

    public void jogUpDn(boolean up){
        if(up){
            k.initJogDist += k.distJog;
        }else{
            k.initJogDist -= k.distJog;
        }
        Display.put("JogUpDn", k.initJogDist);
    }

    public void jogLR(boolean left){
        if(left){
            k.initJogAng -= k.angJog;
        }else{
            k.initJogAng += k.angJog;
        }
        Display.put("JogLR", k.initJogAng);
    }

    //Climber
    public void climbMode(boolean set){
        if(k.shootDisabled && k.climbDisabled) return;
        shootVsClimb.set(set);
    }
    /*public void dropFoot(boolean on){
        if(k.shootDisabled && k.climbDisabled) return;
        dropFoot.set(on);
    }*/
    public void setCamLights(boolean on){
        camLightsSol.set(on);
    }

    public void climbBrake(boolean on){
        climbBrake.set(on);
    }
}