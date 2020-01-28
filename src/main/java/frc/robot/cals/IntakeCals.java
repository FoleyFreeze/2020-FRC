package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class IntakeCals extends CalSet {

    public boolean disabled = true;
    public int solenoid = 1;
    public MotorCal spinMotor = new MotorCal(MotorType.TALON_SRX, 7);

    public IntakeCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}