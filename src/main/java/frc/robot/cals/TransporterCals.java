package frc.robot.cals;

public class TransporterCals extends CalSet {

    public boolean disabled = false;
    public MotorCal rotateMotor = MotorCal.spark(12).pid(0.1, 0, 0.1, 0).invert().limit(0.5).ramp(0.3).brake();
    public MotorCal loadMotor = MotorCal.srx(13).invert(); //this is the gate wheels and the CW motor
    public int CWNotTransport = 5;//3 4 or 5
    public int sensorValue = 5;
    public double hasBallMaxV = 2.0;
    public double hasBallMinV = 1.5;
    public int launcherValue = 2;
    public double countsPerIndex = 24/40.0 * 52/36.0 * 64/20.0 * 64/13.0;
    public double allowedIndexError = 0.2 * countsPerIndex;
    public final double TN_LOADSPEED = 0.2;
    public final double TN_STOPSPEED = -0.0;

    public TransporterCals(){

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