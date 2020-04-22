package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.cals.DriverCals;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RobotState;
import frc.robot.util.Util;
import frc.robot.util.Vector;

public class AutoDrive extends CommandBase{

    private Drivetrain mDriveTrain;
    private RobotState mState;
    private double tgtX;
    private double tgtY;
    private double tgtRot;
    //private double deltaX;
    //private double deltaY;
    private double errorX;
    private double errorY;
    private double startX;
    private double startY;
    private double errorRot;
    private boolean deltaVsField;
    private double[] xPoints;
    private double[] yPoints;
    private double[] rotAngs;
    private int i;//indexer
    
    private DriverCals mCals;

    public AutoDrive(Drivetrain drivetrain, RobotState state, //double deltaX, double deltaY, double angle, 
        boolean deltaVsField, double[] xPoints, double[] yPoints, double[] rotAngs){
            mDriveTrain = drivetrain;
            mState = state;
            addRequirements(mDriveTrain);
            mCals = mDriveTrain.k;
            //this.deltaX = deltaX;
            //this.deltaY = deltaY;
            //this.tgtRot = angle;
            this.deltaVsField = deltaVsField;
            this.xPoints = xPoints;
            this.yPoints = yPoints;
            this.rotAngs = rotAngs;
    }

    @Override
    public void initialize(){
        Pose2d pose = mState.drivePos;
        startX = pose.getTranslation().getX();
        startY = pose.getTranslation().getY();
        if(deltaVsField){
            tgtX = xPoints[0] + startX;
            tgtY = yPoints[0] + startY;
            tgtRot = rotAngs[0];
            //tgtX = startX + deltaX;
            //tgtY = startY + deltaY;
        } else {
            tgtX = xPoints[0];
            tgtY = yPoints[0];
            tgtRot = rotAngs[0];
            //tgtX = deltaX;
            //tgtY = deltaY;
        }
        //SmartDashboard.putNumber("AutoXtgt",tgtX);
        //SmartDashboard.putNumber("AutoYtgt",tgtY);
        i = 0;
    }

    @Override
    public void execute(){
        Pose2d pose = mState.drivePos;
        double x = pose.getTranslation().getX();
        double y = pose.getTranslation().getY();
        errorX = (tgtX - x);
        errorY = (tgtY - y);
        errorRot = Util.angleDiff(tgtRot, mDriveTrain.robotAng);

        Vector strafe = Vector.fromXY(errorY* mCals.autoDriveStrafeKp, -errorX * mCals.autoDriveStrafeKp);

        double power = mCals.autoDriveMaxPwr;
        double dfsX = x - startX;
        double dfsY = y - startY;
        double distFromStart = Math.sqrt(dfsX*dfsX + dfsY*dfsY);
        double distToTarget = Math.sqrt(errorX*errorX + errorY*errorY);
        double startPwr = ((power - mCals.autoDriveStartPwr)/(mCals.autoDriveStartDist)) * distFromStart 
            + mCals.autoDriveStartPwr;
        double endPwr = ((power - mCals.autoDriveEndPwr)/(mCals.autoDriveEndDist)) * distToTarget + mCals.autoDriveEndPwr;
        power = Util.min(power, startPwr, endPwr);

        mDriveTrain.drive(strafe, -errorRot * mCals.autoDriveAngKp, 0, 0, true, power);

        SmartDashboard.putNumber("AutoXerr",errorX);
        SmartDashboard.putNumber("AutoYerr",errorY);
        SmartDashboard.putNumber("AutoAngerr",errorRot);
        SmartDashboard.putNumber("DistFromStart",distFromStart);
        SmartDashboard.putNumber("DistToTarget",distToTarget);
        SmartDashboard.putNumber("AutoPower",power);
    }

    @Override
    public void end(boolean interrupted){
        mDriveTrain.drive(new Vector(0, 0), 0, 0, 0, true);
    }

    @Override
    public boolean isFinished(){
        if(i < xPoints.length-1){
            if(Math.abs(errorX) < mCals.autoDriveStrafeRange 
            && Math.abs(errorY) < mCals.autoDriveStrafeRange 
            /*&& Math.abs(errorRot) < mCals.autoDriveAngRange*/){//don't care about angles if we aren't done
                i++;
                if(deltaVsField){
                    tgtX = xPoints[i] + startX;
                    tgtY = yPoints[i] + startY;
                    tgtRot = rotAngs[i];
                }else{
                    tgtX = xPoints[i];
                    tgtY = yPoints[i];
                    tgtRot = rotAngs[i];
                }
            }
            return false;
        }else{
            return Math.abs(errorX) < mCals.autoDriveStrafeRange 
                && Math.abs(errorY) < mCals.autoDriveStrafeRange 
                && Math.abs(errorRot) < mCals.autoDriveAngRange;
        }
    }
}