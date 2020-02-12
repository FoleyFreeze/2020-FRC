package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.ClimberCals;
import frc.robot.motors.Motor;

public class Climber extends SubsystemBase{

    private ClimberCals mCals;
    private Motor elevatorMotor;
    private Solenoid dropFoot;

    public Climber(ClimberCals cals){
        mCals = cals;

        elevatorMotor = Motor.initMotor(mCals.elevatorMotor);
        dropFoot = mCals.dropFoot;
    }

    public void setPower(double power){
        elevatorMotor.setPower(power);
    }

    public void dropFoot(boolean on){
        dropFoot.set(on);
    }
}