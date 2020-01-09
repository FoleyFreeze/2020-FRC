package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.CannonCals;
import frc.robot.motors.Motor;

public class Cannon extends SubsystemBase{

    private CannonCals mCals;
    private Motor motor;

    public Cannon(CannonCals cals){
        mCals = cals;

        motor = Motor.initMotor(mCals.cannonMotor);
    }
    
    public void setpower(double power){
        motor.setPower(power);
    }

    public void setspeed(double speed){
        motor.setSpeed(speed);
    }
}