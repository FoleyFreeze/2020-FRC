package frc.robot.cals;

import edu.wpi.first.wpilibj.Solenoid;
import frc.robot.cals.MotorCal.*;

public class ClimberCals extends CalSet {

    boolean disabled = true;
    public Solenoid dropFoot = new Solenoid(-1);
    public static double upPower = 0.3;
    public static double dnPower = -0.7;

    public ClimberCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}