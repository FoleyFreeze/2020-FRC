package frc.robot.cals;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.cals.MotorCal.MotorType;

public class IntakeCals extends CalSet {

    public boolean disabled = true;
    public MotorCal spinMotor = new MotorCal(MotorType.TALON_SRX, 7);
    public Solenoid depSol = new Solenoid(-1);
    public double forwardPower = 0.5;
    public double backwardPower = -0.5;

    public IntakeCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}