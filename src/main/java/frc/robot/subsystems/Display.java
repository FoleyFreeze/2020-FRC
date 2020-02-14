package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DisplayCals;

public class Display extends SubsystemBase{

    private DisplayCals mCals;
    private RobotContainer mSubsystem;

    public static HashMap<String, NetworkTableEntry> map = new HashMap<>();

    public Display(DisplayCals cals, RobotContainer subsystem){
        mCals = cals;
        mSubsystem = subsystem;


    }

    public static void init(){
        
    }
}