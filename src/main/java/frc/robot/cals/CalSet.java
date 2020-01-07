package frc.robot.cals;

import edu.wpi.first.wpilibj.DigitalInput;

public class CalSet {

    public static enum BotType {
        COMPETITION, PRACTICE
    }
    public static BotType type;

    public static void identifyBot(){
        DigitalInput di = new DigitalInput(10);//TODO: make this a constant id
        if(di.get()){
            type = BotType.COMPETITION;
        } else {
            type = BotType.PRACTICE;
        }
        di.close();
    } 

    public static boolean isCompBot(){
        return type == BotType.COMPETITION;
    }
    
}