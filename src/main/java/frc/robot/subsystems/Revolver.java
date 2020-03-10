package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.TransporterCals;
import frc.robot.motors.Motor;

public class Revolver extends SubsystemBase{
    
    public TransporterCals k;
    private Motor revMot;
    private int tgtPos = 0;
    public int currPos = 0;
    double tgtInTicks = 0;
    double currInTicks = 0;
    public boolean unjamming = false;
    public boolean jammed = false;
    int currCount = 0;
    
    public Revolver(TransporterCals cals){
        k = cals;
        if(k.disabled) return;
        revMot = Motor.initMotor(k.rotateMotor);
    }
    
    @Override
    public void periodic() {
        if(revMot.getCurrent() >= 30){
            currCount++;
        }else currCount--;
        jammed = currCount > 5;
    }

    public void index(int count){
        if(!unjamming){
            tgtPos += count;
            tgtInTicks += k.countsPerIndex*count;
        } else {
            unjamming = false;
        }
        revMot.setPosition(tgtPos);
    }
    
    public boolean indexing(){
        return !(tgtPos == currPos) && !unjamming;
    }

    public int currPos(){
        return 0;
    }

    public void setPower(double pwr){
        revMot.setPower(pwr);
    }

    public void unjam(){
        unjamming = true;
        setPower(k.unjamPwr);
    }

    public boolean isJamming(){
        return jammed;
    }
}