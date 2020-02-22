package frc.robot.cals;

public class TransporterCals extends CalSet {

    public boolean disabled = true;
    public MotorCal rotateMotor = MotorCal.srx(12);
    public MotorCal loadMotor = MotorCal.srx(-1);
    public int CWNotTransport = -1;
    public int sensorValue = -1;
    public int launcherValue = -1;
    public double countsPerIndex = 40/24 * 36/52 * 20/64 * 13/64 / 4096;//18.0/32.0/5.0*2048.0;
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