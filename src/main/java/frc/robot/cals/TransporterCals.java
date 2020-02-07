package frc.robot.cals;

public class TransporterCals extends CalSet {

    boolean disabled = true;
    public MotorCal rotateMotor = MotorCal.srx(-1);
    public int sensorValue;
    public int launcherValue;
    public double countsPerIndex = 18.0/32.0/5.0*2048.0;

    public TransporterCals(){

        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}