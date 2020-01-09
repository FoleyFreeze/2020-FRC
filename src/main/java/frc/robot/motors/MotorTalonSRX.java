package frc.robot.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.cals.MotorCal;



public class MotorTalonSRX extends Motor{
    
    TalonSRX motor;

    public MotorTalonSRX(MotorCal cal){
        motor = new TalonSRX(cal.id);
    }

    public void setPower(double power){
        motor.set(ControlMode.PercentOutput, power);
    }

    public void setPosition(double position){
        motor.set(ControlMode.Position, position);
    }

    public double getPosition(){
        return motor.getSelectedSensorPosition();
    }    
    
    public void setSpeed(double speed){
        motor.set(ControlMode.Position, speed);
    }
    
}