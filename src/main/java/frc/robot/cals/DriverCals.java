package frc.robot.cals;

public class DriverCals extends CalSet {

    public boolean disabled = false;

    public MotorCal[] driveMotors = {   
        MotorCal.spark( 1).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(14).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(20).ramp(0.3).limit(0.2, 0.2).invert(), 
        MotorCal.spark(15).ramp(0.3).limit(0.2, 0.2).invert()};
    public MotorCal[] turnMotors = {   
        MotorCal.spark( 5).pid(0.000025, 0, 0.0, 0).dFilt(0.3).limit(0.2, 0.2).brake().ramp(0.3), 
        MotorCal.spark(11).pid(0.000025, 0, 0.0, 0).dFilt(0.3).limit(0.2, 0.2).brake().ramp(0.3), 
        MotorCal.spark( 4).pid(0.000025, 0, 0.0, 0).dFilt(0.3).limit(0.2, 0.2).brake().ramp(0.3), 
        MotorCal.spark(10).pid(0.000025, 0, 0.0, 0).dFilt(0.3).limit(0.2, 0.2).brake().ramp(0.3)};
    public int[] turnEncoderIds = {0, 1, 2, 3};
    public double[] xPos = {-12.375, 12.375, -12.375, 12.375};
    public double[] yPos = {10.625, 10.625, -10.625, -10.625};
    public double[] angleOffset;

    public boolean scaleNormalize = true;

    public double turnGearRatio = 20.0/60.0 * 20.0/60.0 * 40.0/64.0;


    public DriverCals(){

        switch(type){
            case COMPETITION:
                angleOffset = new double[]{1.01,4.62,0.73,2.99};
            break;

            case PRACTICE:
                angleOffset = new double[]{0, 0, 0, 0};
            break;
        }

    }

}