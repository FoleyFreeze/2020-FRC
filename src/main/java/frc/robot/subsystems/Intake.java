package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeCals;
import frc.robot.motors.Motor;

public class Intake extends SubsystemBase {

    public Motor spinmotor;
    private IntakeCals mCals;

    public Intake(IntakeCals cals){
        this.mCals = cals;

        spinmotor = Motor.initMotor(mCals.spinMotor);
    }

    public void setPower(double power){
        spinmotor.setPower(power);
    }
    public void setSpeed(double speed){
        spinmotor.setSpeed(speed);
    }
}