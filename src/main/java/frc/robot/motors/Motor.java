package frc.robot.motors;

import frc.robot.cals.MotorCal;

public abstract class Motor{  

    public static Motor initMotor(MotorCal cal){
        switch(cal.type){
            case PWM_TALON:
                return new MotorTalonPWM(cal);

            case SPARK_MAX:
                return new MotorSparkMax(cal);

            case TALON_SRX:
                return new MotorTalonSRX(cal);

            default:
                return null;
        }
    }

    public abstract void setPower(double power);
    public abstract void setPosition(double position);
    public abstract void setSpeed(double speed);

}