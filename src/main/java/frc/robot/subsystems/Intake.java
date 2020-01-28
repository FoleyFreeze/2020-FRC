package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeCals;
import frc.robot.motors.Motor;

public class Intake extends SubsystemBase {

    public Motor spinmotor;
    private IntakeCals mCals;
    public Solenoid mSol;

    public Intake(IntakeCals cals){
        this.mCals = cals;

        if(mCals.disabled) return;
        //spinmotor = Motor.initMotor(mCals.spinMotor);
        mSol = new Solenoid(mCals.solenoid);
    }

    public void setPower(double power){
        if(mCals.disabled) return;
        spinmotor.setPower(power);
    }
    public void setSpeed(double speed){
        if(mCals.disabled) return;
        spinmotor.setSpeed(speed);
    }
    public void solExtend(boolean extend){
        if(mCals.disabled) return;
        mSol.set(extend);
    }
}