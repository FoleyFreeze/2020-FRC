package frc.robot.cals;

import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.subsystems.Display;

public class CalSet {

    public static enum BotType {
        COMPETITION, PRACTICE
    }
    public static BotType type;

    public static void identifyBot(){
        DigitalInput di = new DigitalInput(9);//TODO: make this a constant id
        if(di.get()){
            type = BotType.COMPETITION;
        } else {
            type = BotType.PRACTICE;
        }
        di.close();

        Display.put("Comp/Prac", type.toString());
    } 

    public static boolean isCompBot(){
        return type == BotType.COMPETITION;
    }
    
}