package frc.robot.cals;

import frc.robot.relays.Relay;

public class PneumaticsCals extends CalSet{
    
    private Relay solRelay;
    
    public PneumaticsCals(Relay solRelay){
        this.solRelay = solRelay;
    }
}