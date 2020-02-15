package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import frc.robot.cals.MotorCal;



public class MotorSparkMax extends Motor{
    
    MotorCal cals;
    CANSparkMax motor;
    
    public MotorSparkMax(MotorCal cal){
        cals = cal;
        motor = new CANSparkMax(cal.id, 
            com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
        motor.restoreFactoryDefaults();

        if(cal.brake){
            motor.setIdleMode(IdleMode.kBrake);
        }else{
            motor.setIdleMode(IdleMode.kCoast);
        }

        motor.setOpenLoopRampRate(cal.rampRate);

        if(cals.pid){
            motor.setClosedLoopRampRate(cal.rampRate);
            motor.getPIDController().setP(cal.kP);
            motor.getPIDController().setI(cal.kI);
            motor.getPIDController().setD(cal.kD);
            motor.getPIDController().setFF(cal.kF);
            motor.getPIDController().setDFilter(cal.kDFilt);
            motor.getPIDController().setOutputRange(-cal.minPower, 
                cal.maxPower);
            motor.setClosedLoopRampRate(cal.rampRate);
        }
        
        motor.setInverted(cal.invert);
    }

    public void setPower(double power){
        if(power > 0) power *= cals.maxPower;
        else if(power < 0) power *= cals.minPower;
        //System.out.println(power);
        motor.set(power);
    }

    public void setPosition(double position){
        motor.getPIDController().setReference(position, 
            ControlType.kPosition);
    }

    public double getPosition(){
        return motor.getEncoder().getPosition();
    }
    
    public void setSpeed(double speed){
        motor.getPIDController().setReference(speed, 
            ControlType.kVelocity);
    }

    public double getCurrent(){
        return motor.getOutputCurrent();
    }

    public double getTemp(){
        return motor.getMotorTemperature();
    }
}