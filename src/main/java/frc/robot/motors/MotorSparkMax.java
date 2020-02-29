package frc.robot.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Timer;
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
            motor.getPIDController().setOutputRange(cal.minPower, 
                cal.maxPower);
            motor.setClosedLoopRampRate(cal.rampRate);
        }
        
        motor.setInverted(cal.invert);

        motor.getEncoder().setPosition(0);
    }

    public void setPower(double power){
        if(power > 1) power = 1;
        else if(power < -1) power = -1;

        if(power > 0) power *= cals.maxPower;
        else if(power < 0) power *= -cals.minPower;
        
        
        if(getCurrent() > cals.currentLimit){
            overCurrentCount++;
        } else {
            if(overCurrentCount > 0) overCurrentCount--;
        }

        if(overCurrentCount > cals.overCurrentCountLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overCurrentRestTime;
        }

        if(getTemp() > cals.tempLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overTempRestTime;
        }

        if(Timer.getFPGATimestamp() < currentTimer){
            motor.set(power);
        } else {
            motor.set(0);
        }
    }

    double currentTimer = 0;
    int overCurrentCount = 0;
    public void setPosition(double position){
        if(getCurrent() > cals.currentLimit){
            overCurrentCount++;
        } else {
            if(overCurrentCount > 0) overCurrentCount--;
        }

        if(overCurrentCount > cals.overCurrentCountLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overCurrentRestTime;
        }

        if(getTemp() > cals.tempLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overTempRestTime;
        }

        if(Timer.getFPGATimestamp() < currentTimer){
            motor.getPIDController().setReference(position, 
            ControlType.kPosition);    
        } else {
            motor.set(0);
        }
    }

    public double getPosition(){
        return motor.getEncoder().getPosition();
    }
    
    public void setSpeed(double speed){
        if(getCurrent() > cals.currentLimit){
            overCurrentCount++;
        } else {
            if(overCurrentCount > 0) overCurrentCount--;
        }

        if(overCurrentCount > cals.overCurrentCountLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overCurrentRestTime;
        }

        if(getTemp() > cals.tempLimit){
            currentTimer = Timer.getFPGATimestamp() + cals.overTempRestTime;
        }

        if(Timer.getFPGATimestamp() < currentTimer){
            motor.getPIDController().setReference(speed, 
            ControlType.kVelocity);
        } else {
            motor.set(0);
        }
    }

    public double getSpeed(){
        return motor.getEncoder().getVelocity();
    }

    public double getCurrent(){
        return motor.getOutputCurrent();
    }

    public double getTemp(){
        return motor.getMotorTemperature();
    }
}