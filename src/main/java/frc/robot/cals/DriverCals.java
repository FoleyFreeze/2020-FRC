package frc.robot.cals;

public class DriverCals extends CalSet {

    public boolean disabled = true;
    public MotorCal[] driveMotors = {   
        MotorCal.spark(1).ramp(0.3), 
        MotorCal.spark(14).ramp(0.3), 
        MotorCal.spark(20).ramp(0.3), 
        MotorCal.spark(15).ramp(0.3)};
    public MotorCal[] turnMotors = {
        MotorCal.spark( 5).pid(0.25, 0, 0.2, 0).dFilt(0.3).limit(-0.5, 0.5).brake().ramp(0.3), 
        MotorCal.spark(11).pid(0.25, 0, 0.2, 0).dFilt(0.3).limit(-0.5, 0.5).brake().ramp(0.3), 
        MotorCal.spark( 4).pid(0.25, 0, 0.2, 0).dFilt(0.3).limit(-0.5, 0.5).brake().ramp(0.3), 
        MotorCal.spark(10).pid(0.25, 0, 0.2, 0).dFilt(0.3).limit(-0.5, 0.5).brake().ramp(0.3)};
    public int[] turnEncoderIds = {0, 1, 2, 3};
    public double[] xPos = {-12.375, 12.375, -12.375, 12.375};
    public double[] yPos = {10.625, 10.625, -10.625, -10.625};

    public boolean scaleNormalize = true;

    public double turnGearRatio = 20/60 * 20/60 * 40/64;


    public DriverCals(){

        switch(type){
            case COMPETITION:
                
            break;

            case PRACTICE:
                
            break;
        }

    }

}