package frc.robot.subsystems;

import java.util.HashMap;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Display extends SubsystemBase{

    public static HashMap<String, NetworkTableEntry> map = new HashMap<>();
    static NetworkTableEntry error;

    public Display(){
    }

    public static void addToTab(ShuffleboardTab tab, String name, double init, int x, int y){
        NetworkTableEntry nte = tab.add(name, init).withPosition(x, y).getEntry();
        map.put(name,nte);
    }
    public static void addToTab(ShuffleboardTab tab, String name, String init, int x, int y){
        NetworkTableEntry nte = tab.add(name, init).withPosition(x, y).getEntry();
        map.put(name,nte);
    }
    public static void addToTab(ShuffleboardTab tab, String name, boolean init, int x, int y){
        NetworkTableEntry nte = tab.add(name, init).withPosition(x, y).getEntry();
        map.put(name,nte);
    }

    public static void init(){
        ShuffleboardTab tab = Shuffleboard.getTab("Comp");
            addToTab(tab, "Selected Auton", "default", 1, 0);
            addToTab(tab, "Pi Alive", false, 2, 0);
            String name;
            NetworkTableEntry nte;
            name = "Last Target";
            nte = tab.add(name, "time, dist, ang").withPosition(3, 0).getEntry();
            map.put(name, nte);
            name = "Last";
            nte = tab.add("Last Ball", "time, dist, ang").withPosition(4, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("DT", 0.0).withPosition(5, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("Image DT", 0.0).withPosition(6, 0).getEntry();
            map.put(nte.getName(), nte);
            addToTab(tab, "Robo Pos", "x, y", 7, 0);
            addToTab(tab, "NavX Ang", 0, 0, 1);
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
            error = nte;
            System.out.println("Error Name");
            System.out.println(nte.getName());
            addToTab(tab, "RobotType", "None", 7,1);
        
        tab = Shuffleboard.getTab("DriveTrain");
            addToTab(tab, "DMotorCurrent 0", 0, 0, 0);
            addToTab(tab, "DMotorCurrent 1", 0, 0, 1);
            addToTab(tab, "DMotorCurrent 2", 0, 0, 2);
            addToTab(tab, "DMotorCurrent 3", 0, 0, 3);
            addToTab(tab, "DMotorTemp 0", 0, 1, 0);
            addToTab(tab, "DMotorTemp 1", 0, 1, 1);
            addToTab(tab, "DMotorTemp 2", 0, 1, 2);
            addToTab(tab, "DMotorTemp 3", 0, 1, 3);
            addToTab(tab, "TMotorCurrent 0", 0, 4, 0);
            addToTab(tab, "TMotorCurrent 1", 0, 4, 1);
            addToTab(tab, "TMotorCurrent 2", 0, 4, 2);
            addToTab(tab, "TMotorCurrent 3", 0, 4, 3);
            addToTab(tab, "TMotorTemp 0", 0, 6, 0);
            addToTab(tab, "TMotorTemp 1", 0, 6, 1);
            addToTab(tab, "TMotorTemp 2", 0, 6, 2);
            addToTab(tab, "TMotorTemp 3", 0, 6, 3);
            
            nte = tab.add("Field Relative Pos", "x, y").withPosition(2, 0).getEntry();
            map.put(nte.getName(), nte);
            nte = tab.add("NavX Ang", 0.0).withPosition(3, 0).getEntry();
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
            error.setString(name);
        }
    }

    public static void put(String name, boolean data){
        if(map.containsKey(name)){
            map.get(name).setBoolean(data);
        } else {
            error.setString(name);
        }
    }

    public static void put(String name, String data){
        if(map.containsKey(name)){
            map.get(name).setString(data);
        } else {
            error.setString(name);
        }
    }
}