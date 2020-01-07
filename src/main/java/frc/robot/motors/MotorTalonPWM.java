package frc.robot.motors;

import edu.wpi.first.wpilibj.Talon;
import frc.robot.cals.MotorCal;


public class MotorTalonPWM extends Motor {

    Talon motor;

    public MotorTalonPWM(MotorCal cal){
        motor = new Talon(cal.id);
    }

    public void setPower(double power){
        motor.set(power);
    }

    public void setPosition(double position){
        throw new UnsupportedOperationException();
    }

}