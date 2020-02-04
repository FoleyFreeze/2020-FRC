package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.CannonCals;
import frc.robot.motors.Motor;

public class Cannon extends SubsystemBase{

    private CannonCals mCals;
    private Motor motor;

    public Cannon(CannonCals cals){
        mCals = cals;
        if(cals.disabled) return;

        motor = Motor.initMotor(mCals.cannonMotor);
    }
    
    public void setpower(double power){
        if(mCals.disabled) return;
        motor.setPower(power);
    }

    public void setspeed(double speed){
        if(mCals.disabled) return;
        motor.setSpeed(speed);
    }
}