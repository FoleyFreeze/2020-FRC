package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class DriverCals extends CalSet {

    boolean disabled = true;
    MotorCal driveLF = new MotorCal(MotorType.SPARK_MAX, -1);

    public DriverCals(){

        switch(type){
            case COMPETITION:
                
            break;

            case PRACTICE:
                
            break;
        }

    }

}