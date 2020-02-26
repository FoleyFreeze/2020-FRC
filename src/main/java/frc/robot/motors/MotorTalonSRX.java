package frc.robot.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.robot.cals.MotorCal;



public class MotorTalonSRX extends Motor{
    
    TalonSRX motor;
    MotorCal cal;

    public MotorTalonSRX(MotorCal cal){
        this.cal = cal;
        motor = new TalonSRX(cal.id);

        motor.configFactoryDefault();
        motor.setInverted(cal.invert);
        if(cal.brake){
            motor.setNeutralMode(NeutralMode.Brake);
        } else {
            motor.setNeutralMode(NeutralMode.Coast);
        }

    }

    public void setPower(double power){
        if(power > cal.maxPower) power = cal.maxPower;
        if(power < cal.minPower) power = cal.minPower;
        motor.set(ControlMode.PercentOutput, power);
    }

    public void setPosition(double position){
        motor.set(ControlMode.Position, position);
    }

    public double getPosition(){
        return motor.getSelectedSensorPosition();
    }    
    
    public void setSpeed(double speed){
        motor.set(ControlMode.Position, speed * 2048/600);
    }
    
    public double getCurrent(){
        return motor.getStatorCurrent();
    }

    public double getTemp(){
        return 0;
    }

    @Override
    public double getSpeed() {
        return motor.getSelectedSensorVelocity() * 600/2048;
    }
}