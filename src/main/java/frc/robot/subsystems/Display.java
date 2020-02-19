package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Display extends SubsystemBase{

    public static HashMap<String, NetworkTableEntry> map = new HashMap<>();

    public Display(){
    }

    public static void init(){
        ShuffleboardTab tab = Shuffleboard.getTab("Comp");
            NetworkTableEntry nte = tab.add("Selected Auton", "default").withPosition(1,0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Pi Alive", false).withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Last Target", "time, dist, ang").withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Last Ball", "time, dist, ang").withPosition(4, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DT", 0.0).withPosition(5, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Image DT", 0.0).withPosition(6, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Robo Pos", "x, y").withPosition(7, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("NavX Ang", 0.0).withPosition(0, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Motors Good", true).withPosition(1, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Number", 1).withPosition(2, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("FieldColor", "nope").withPosition(3, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CompressorRun", false).withPosition(4, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Pressure", 0.0).withPosition(5, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Pit Mode", false).withPosition(4, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Last Error", "N/A").withPosition(6, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Comp/Prac", "N/A").withPosition(7, 1).getEntry();
            map.put(nte.getName(), nte);
        
        tab = Shuffleboard.getTab("DriveTrain");
            nte = tab.add("DMotorCurrent 0", 0.0).withPosition(0, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorCurrent 1", 0.0).withPosition(0, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorCurrent 2", 0.0).withPosition(0, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorCurrent 3", 0.0).withPosition(0, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorTemp 0", 0.0).withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorTemp 1", 0.0).withPosition(1, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorTemp 2", 0.0).withPosition(1, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DMotorTemp 3", 0.0).withPosition(1, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorTemp 0", 0.0).withPosition(6, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorTemp 1", 0.0).withPosition(6, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorTemp 2", 0.0).withPosition(6, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorTemp 3", 0.0).withPosition(6, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Field Relative Pos", "x, y").withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("NavX Ang", 0.0).withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorCurrent 0", 0.0).withPosition(4, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorCurrent 1", 0.0).withPosition(4, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorCurrent 2", 0.0).withPosition(4, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TMotorCurrent 3", 0.0).withPosition(4, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DistSenseInfo Ri", "default").withPosition(7, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DistSenseInfo Re", "default").withPosition(7, 1).getEntry();
            map.put(nte.getName(), nte);

        tab = Shuffleboard.getTab("Climber");
            nte = tab.add("Elevator Pos", 0.0).withPosition(0, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Foot Dropped", false).withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CCMotorCurrent 0", 0.0).withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CCMotorCurrent 1", 0.0).withPosition(2, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CC Motor Temp 0", 0.0).withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CC Motor Temp 1", 0.0).withPosition(3, 1).getEntry();
            map.put(nte.getName(), nte);

        tab = Shuffleboard.getTab("Intake");
            nte = tab.add("InMotorCurr", 0.0).withPosition(0, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Extended", false).withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Spotted", false).withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Info", "dist, ang").withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);

        tab = Shuffleboard.getTab("Cannon");
            nte = tab.add("RPM", 0.0).withPosition(0, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Number", 0).withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Hood Pos", "pos").withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Last Target Info", "dist, ang").withPosition(0, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Has Target", false).withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CCMotorCurrent 0", 0.0).withPosition(4, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CCMotorCurrent 1", 0.0).withPosition(4, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CC Motor Temp 0", 0.0).withPosition(5, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("CC Motor Temp 1", 0.0).withPosition(5, 1).getEntry();
            map.put(nte.getName(), nte);


        tab = Shuffleboard.getTab("Transporter");
            
            nte = tab.add("TCMotorCurrent0", 0.0).withPosition(0, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TCMotorCurrent1", 0.0).withPosition(0, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TC Motor Temp 0", 0.0).withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TC Motor Temp 1", 0.0).withPosition(1, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Number", 0).withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Position 0", false).withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Position 1", false).withPosition(3, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Position 2", false).withPosition(3, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Position 3", false).withPosition(3, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Ball Position 4", false).withPosition(3, 4).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Current Pos", 0).withPosition(4, 0).getEntry();
            map.put(nte.getName(), nte);

        tab = Shuffleboard.getTab("Color Wheel");
            nte = tab.add("TCMotorCurrent0", 0.0).withPosition(0,0).getEntry();
            map.put(nte.getName(), nte);  
            nte = tab.add("TCMotorCurrent1", 0.0).withPosition(0, 1).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TC Motor Temp 0", 0.0).withPosition(0, 2).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("TC Motor Temp 1", 0.0).withPosition(0, 3).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Color Info", "RGB, Ir, p").withPosition(1, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Field Given Color", "null").withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Detected Color", "null").withPosition(3, 0).getEntry();
            map.put(nte.getName(), nte);
    }

    public static void put(String name, double data){
        if(map.containsKey(name)){
            map.get(name).setDouble(data);
        } else {
            //map.get("Last Error").setString(name);
        }
    }

    public static void put(String name, boolean data){
        if(map.containsKey(name)){
            map.get(name).setBoolean(data);
        } else {
            //map.get("Last Error").setString(name);
        }
    }

    public static void put(String name, String data){
        if(map.containsKey(name)){
            map.get(name).setString(data);
        } else {
            //map.get("Last Error").setString(name);

        }
    }
}