package frc.robot.cals;

public class TransporterCals extends CalSet {

    public boolean disabled = true;
    public MotorCal rotateMotor = MotorCal.spark(12).pid(0.001, 0, 0, 0).limit(0.1).ramp(0.3).brake();
    public MotorCal loadMotor = MotorCal.nullMtr(-1); //this is the gate wheels and the CW motor
    public int CWNotTransport = -1;
    public int sensorValue = -1;
    public int launcherValue = -1;
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