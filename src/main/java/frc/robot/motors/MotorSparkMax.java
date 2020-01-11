package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.cals.MotorCal;



public class MotorSparkMax extends Motor{
    
    CANSparkMax motor;
    
    public MotorSparkMax(MotorCal cal){
        motor = new CANSparkMax(cal.id, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
        if(cal.brake){
            motor.setIdleMode(IdleMode.kBrake);
        }else{
            motor.setIdleMode(IdleMode.kCoast);
        }

        motor.setOpenLoopRampRate(cal.rampRate);
        motor.getPIDController().setP(cal.kP);
        motor.getPIDController().setI(cal.kI);
        motor.getPIDController().setD(cal.kD);
        motor.getPIDController().setFF(cal.kF);
        motor.getPIDController().setDFilter(cal.kDFilt);
        motor.getPIDController().setOutputRange(cal.minPower, cal.maxPower);
    }

    public void setPower(double power){
        motor.set(power);
    }

    public void setPosition(double position){
        motor.getPIDController().setReference(position, ControlType.kPosition);
    }

    public double getPosition(){
        return motor.getEncoder().getPosition();
    }

}