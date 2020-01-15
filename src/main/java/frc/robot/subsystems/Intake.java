package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeCals;
import frc.robot.motors.Motor;

public class Intake extends SubsystemBase {

    public Motor spinmotor;
    private IntakeCals spinCals;

    public Intake(IntakeCals spinCals){
        this.spinCals = spinCals;

        spinmotor = Motor.initMotor(spinCals.spinMotor);
    }

    public void setPower(double power){
        spinmotor.setPower(power);
    }
    public void setSpeed(double speed){
        spinmotor.setSpeed(speed);
    }
}