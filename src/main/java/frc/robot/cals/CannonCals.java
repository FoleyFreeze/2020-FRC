package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class CannonCals extends CalSet {

    public boolean disabled = true;
    public MotorCal cannonMotor = new MotorCal(MotorType.SPARK_MAX, -1);

    public CannonCals(){
        
        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}