package frc.robot.cals;

public class ClimberCals extends CalSet {

    public boolean disabled = false;
    public int dropFootValue = 4;//3 4 or 5
    public static double upPower = 0.3;
    public static double dnPower = -0.3;

    public ClimberCals(){

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