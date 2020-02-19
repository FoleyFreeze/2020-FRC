package frc.robot.cals;

public class PneumaticsCals extends CalSet{
    public final double minPressure = 60;
    public final double maxPressure = 115;
    public final double hystPressure = 10;
    public final boolean disabled = true;
    public final double pauseTime = 5.0;

    public final int PNE_PSENSORID = -1;

    public PneumaticsCals(){
        switch(type){
            case COMPETITION:

            break;

            case PRACTICE:

            break;
        }
    }
}