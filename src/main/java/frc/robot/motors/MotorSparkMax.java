package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import frc.robot.cals.MotorCal;



public class MotorSparkMax extends Motor{
    
    CANSparkMax motor;
    
    public MotorSparkMax(MotorCal cal){
        motor = new CANSparkMax(cal.id, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void setPower(double power){
        motor.set(power);
    }

    public void setPosition(double position){
        motor.getPIDController().setReference(position, ControlType.kPosition);
    }

    public void setSpeed(double speed){
        motor.getPIDController().setReference(speed, ControlType.kVelocity);
    }

}