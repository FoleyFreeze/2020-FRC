package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeCals;
import frc.robot.motors.Motor;

public class Intake extends SubsystemBase {

    public Motor spinmotor;
    public Solenoid depSol;
    public IntakeCals mCals;

    public Intake(IntakeCals cals){
        this.mCals = cals;
        if(mCals.disabled) return;

        spinmotor = Motor.initMotor(mCals.spinMotor);
        depSol = new Solenoid(mCals.depSolValue);
    }

    public void periodic(){
        
    }
    public void setPower(double power){
        if(mCals.disabled) return;
        spinmotor.setPower(power);
    }
    public void setSpeed(double speed){
        if(mCals.disabled) return;
        spinmotor.setSpeed(speed);
    }
    
    public void dropIntake(boolean activate){
        if(mCals.disabled) return;
        depSol.set(activate);
    }
}