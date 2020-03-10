package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeCals;
import frc.robot.motors.Motor;

public class Intake extends SubsystemBase {

    public Motor spinmotor;
    public Solenoid depSol;
    public IntakeCals k;

    public Intake(IntakeCals cals){
        this.k = cals;
        if(k.disabled) return;
        spinmotor = Motor.initMotor(k.spinMotor);
        depSol = new Solenoid(k.depSolValue);
    }

    double jamTime;
    boolean prevJammed = false;
    public void periodic(){
        if(k.disabled) return;
        Display.put("InMotorCurr", spinmotor.getCurrent());
    }
    public void setPower(double power){
        if(k.disabled) return;
        spinmotor.setPower(power);
    }
    public void setSpeed(double speed){
        if(k.disabled) return;
        spinmotor.setSpeed(speed);
    }
    
    public void dropIntake(boolean activate){
        if(k.disabled) return;
        depSol.set(activate);
    }

    
}