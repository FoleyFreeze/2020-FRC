package frc.robot.subsystems;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.VisionCals;
import frc.robot.util.LimitedList;

public class Vision extends SubsystemBase{
    public class VisionData{
        public double dist;
        public double angle;
        public double timestamp;
        public double robotangle;
    }

    public VisionCals cals;
    public double lastFrameTime;
    public LimitedList<VisionData> targetData;
    public LimitedList<VisionData> ballData;

    public Vision(VisionCals cals){
        targetData = new LimitedList<>(cals.historySize);
        ballData = new LimitedList<>(cals.historySize);
        addNTListener();
        this.cals = cals;
    }

    public boolean hasTargetImage(){
        VisionData vd = new VisionData();
        if(targetData.getFirst() == null){
            return false;
        } else return Timer.getFPGATimestamp() - vd.timestamp < cals.maxImageTime;
    }

    public boolean hasBallImage(){
        VisionData vd = new VisionData();
        if(ballData.getFirst() == null){
            return false;
        } else return Timer.getFPGATimestamp() - vd.timestamp < cals.maxImageTime;
    }

    private void addNTListener(){
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("Vision");

        nt.addEntryListener("Target", (table, key, entry, value, flags) -> {
            try{
                VisionData vd = new VisionData();
                String data = value.getString();
                String[] parts = data.split(",");

                vd.dist = Double.parseDouble(parts[1]);
                vd.angle = Double.parseDouble(parts[2]);

                vd.timestamp = Timer.getFPGATimestamp();
                double dt = vd.timestamp - lastFrameTime;
                SmartDashboard.putNumber("Image dt", dt);
                lastFrameTime = vd.timestamp;

                targetData.addFirst(vd);
            } catch(Exception e){
                e.printStackTrace();
            }

        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
        
        nt.addEntryListener("Ball", (table, key, entry, value, flags) -> {
            try{
                VisionData vd = new VisionData();
                String data = value.getString();
                String[] parts = data.split(",");

                vd.dist = Double.parseDouble(parts[1]);
                vd.angle = Double.parseDouble(parts[2]);

                vd.timestamp = Timer.getFPGATimestamp();
                double dt = vd.timestamp - lastFrameTime;
                SmartDashboard.putNumber("Image dt", dt);
                lastFrameTime = vd.timestamp;

                ballData.addFirst(vd);
            } catch(Exception e){
                e.printStackTrace();
            }

        }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
    }
}