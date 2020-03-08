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
    
    public Revolver(TransporterCals cals){
        k = cals;
        if(k.disabled) return;
        revMot = Motor.initMotor(k.rotateMotor);
    }

    public void index(int count){
        tgtPos += count;
        tgtInTicks += k.countsPerIndex*count;
    }
    
    public boolean indexing(){
        return !(tgtPos == currPos);
    }

    public int currPos(){
        return 0;
    }

    public void gatherIndex(){
        double error = tgtPos - currPos;
        double pwr = error * 0.01;
        revMot.setPower(pwr);
    }
}