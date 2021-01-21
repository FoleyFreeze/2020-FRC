package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DriverCals;
import frc.robot.util.Util;
import frc.robot.util.Vector;
import frc.robot.util.Waypoint;

public class AutoPath extends CommandBase{

    private RobotContainer m_subsystem;
   
    public boolean useFake = false;
    public double fakeX,fakeY,fakeTheta;

    private double errorX;
    private double errorY;
    private double startX;
    private double startY;
    private double errorRot;
    private Waypoint[] path;
    private double lookAhead;
    private int pathIdx;
    private double[] ratios;
    public Waypoint tgtPt;
    
    private DriverCals mCals;

    public AutoPath(RobotContainer subsystem, Waypoint[] path, double lookAhead){
        if(path == null) return;

        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);
        mCals = m_subsystem.m_drivetrain.k;
        this.path = path;
        this.lookAhead = lookAhead;
        ratios = new double[path.length - 1];
    }

    @Override
    public void initialize(){
        if(path == null) return;

        m_subsystem.m_drivetrain.setStartPosition(path[0].x, path[0].y);

        pathIdx = 1;
        Pose2d pose = m_subsystem.m_drivetrain.drivePos;
        if(useFake){
            startX = fakeX;
            startY = fakeY;
        } else {
            startX = pose.getTranslation().getX();// + path[0].x;
            startY = pose.getTranslation().getY();// + path[0].y;
        }
        
        tgtPt = path[1];

        //SmartDashboard.putNumber("AutoXtgt",tgtX);
        //SmartDashboard.putNumber("AutoYtgt",tgtY);
    }

    @Override
    public void execute(){
        if(path == null) return;
        
        double x,y,theta;
        if(useFake){
            x = fakeX;
            y = fakeY;
            theta = fakeTheta;
        } else {
            Pose2d pose = m_subsystem.m_drivetrain.drivePos;
            x = pose.getTranslation().getX();
            y = pose.getTranslation().getY();
            theta = m_subsystem.m_drivetrain.robotAng;
        }
        tgtPt = calcTgt(x, y);

        errorX = (tgtPt.x - x);
        errorY = (tgtPt.y - y);
        errorRot = Util.angleDiff(tgtPt.theta, theta);

        Vector strafe = Vector.fromXY(errorX* mCals.autoDriveStrafeKp, errorY * mCals.autoDriveStrafeKp);

        double power = mCals.autoDriveMaxPwr;
        /*double dfsX = x - startX;
        double dfsY = y - startY;
        double distFromStart = Math.sqrt(dfsX*dfsX + dfsY*dfsY);
        double distToTarget = Math.sqrt(errorX*errorX + errorY*errorY);
        double startPwr = ((power - mCals.autoDriveStartPwr)/(mCals.autoDriveStartDist)) * distFromStart 
            + mCals.autoDriveStartPwr;
        double endPwr = ((power - mCals.autoDriveEndPwr)/(mCals.autoDriveEndDist)) * distToTarget + mCals.autoDriveEndPwr;
        power = Util.min(power, startPwr, endPwr);*/

        if(!useFake){
            m_subsystem.m_drivetrain.drive(strafe, -errorRot * mCals.autoDriveAngKp, 0, 0, true, power);
        }

        SmartDashboard.putNumber("AutoXerr",errorX);
        SmartDashboard.putNumber("AutoYerr",errorY);
        SmartDashboard.putNumber("AutoAngerr",errorRot);
        //SmartDashboard.putNumber("DistFromStart",distFromStart);
        //SmartDashboard.putNumber("DistToTarget",distToTarget);
        SmartDashboard.putNumber("AutoPower",power);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_drivetrain.drive(new Vector(0, 0), 0, 0, 0, true);
    }

    @Override
    public boolean isFinished(){
        //dont allow it to return early
        if(pathIdx+1 < path.length) return false;
        System.out.println("PathIdx: " + pathIdx);

        return Math.abs(errorX) < mCals.autoDriveStrafeRange 
            && Math.abs(errorY) < mCals.autoDriveStrafeRange 
            && Math.abs(errorRot) < mCals.autoDriveAngRange;
    }

    private Waypoint calcTgt(double botX, double botY){
        //use to track how far we have looked ahead(use to determine if we've looked ahead enough)
        double sumDist = calcDist(tgtPt, botX, botY);
        int idx = pathIdx;
        Waypoint tgt = tgtPt;

        while(idx < path.length-1 && sumDist < lookAhead){
            idx++;
            double dist = calcDist(tgt, path[idx]);
            System.out.println("Dist: " + dist);
            if(sumDist + dist >= lookAhead){
                double ratio = (lookAhead - sumDist)/dist;
                tgt = new Waypoint(
                    (path[idx].x - path[idx-1].x)*ratio + path[idx-1].x,
                    (path[idx].y - path[idx-1].y)*ratio + path[idx-1].y,
                    Util.angleDiff(path[idx].theta, path[idx-1].theta)*ratio + path[idx-1].theta
                );
            } else{
                tgt = path[idx];
            }
            sumDist += dist;
        }

        System.out.println(tgt.toString());
        return tgt;
    }

    //distance formula
    private double calcDist(Waypoint p1, double x, double y){
        double dx = x - p1.x;
        double dy = y - p1.y;
        return(Math.sqrt(dx * dx + dy * dy));
    }

    private double calcDist(Waypoint p1, Waypoint p2){
        return calcDist(p1, p2.x, p2.y);
    }
}