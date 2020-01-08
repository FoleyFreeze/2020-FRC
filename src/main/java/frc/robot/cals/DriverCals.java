package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class DriverCals extends CalSet {

    public boolean disabled = true;
    public MotorCal[] driveMotors = {
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1)};
    public MotorCal[] turnMotors = {
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1), 
        MotorCal.newSpark(-1)};
    public int[] turnEncoderIds = {-1,-1,-1,-1};
    public int[] xPos = {-1, -1, -1, -1};
    public int[] yPos = {-1, -1, -1, -1};


    public DriverCals(){

        switch(type){
            case COMPETITION:
                
            break;

            case PRACTICE:
                
            break;
        }

    }

}