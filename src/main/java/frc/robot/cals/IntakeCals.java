package frc.robot.cals;

import frc.robot.cals.MotorCal.MotorType;

public class IntakeCals extends CalSet {

    public boolean disabled = false;
    public MotorCal spinMotor = new MotorCal(MotorType.SPARK_MAX, 6);//.invert(); //MrC
    //public MotorCal spinMotor = new MotorCal(MotorType.SPARK_MAX, 6).pid(0.0004, 0, 0, 0.0003).ramp(10).limit(0, 1);
    public int depSolValue = 7;
    public double forwardPower = 0.25;
    public double backwardPower = -0.2;
    public double idxPower = 0.2;

    public IntakeCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;

            case LASTYEAR:

            break;
        }
    }
}