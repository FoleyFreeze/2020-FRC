package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DriverCals;
import frc.robot.util.Util;
import frc.robot.util.Vector;

public class AutoDrive extends CommandBase{

    private RobotContainer m_subsystem;
    private double tgtX;
    private double tgtY;
    private double tgtRot;
    private double deltaX;
    private double deltaY;
    private double errorX;
    private double errorY;
    private double errorRot;
    private boolean deltaVsField;
    
    private DriverCals mCals;

    public AutoDrive(RobotContainer subsystem, double deltaX, double deltaY, double angle, boolean deltaVsField){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);
        mCals = m_subsystem.m_drivetrain.k;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.tgtRot = angle;
        this.deltaVsField = deltaVsField;
    }

    @Override
    public void initialize(){
        Pose2d pose = m_subsystem.m_drivetrain.drivePos;
        if(deltaVsField){
            tgtX = pose.getTranslation().getX() + deltaX;
            tgtY = pose.getTranslation().getY() + deltaY;
        } else {
            tgtX = deltaX;
            tgtY = deltaY;
        }
        SmartDashboard.putNumber("AutoXtgt",tgtX);
        SmartDashboard.putNumber("AutoYtgt",tgtY);
    }

    @Override
    public void execute(){
        Pose2d pose = m_subsystem.m_drivetrain.drivePos;
        errorX = (tgtX - pose.getTranslation().getX());
        errorY = (tgtY - pose.getTranslation().getY());
        errorRot = tgtRot - m_subsystem.m_drivetrain.robotAng;
        if(errorRot > 180) errorRot-= 360;
        else if(errorRot < -180) errorRot+=360;

        Vector strafe = Vector.fromXY(errorY* mCals.autoDriveStrafeKp, -errorX * mCals.autoDriveStrafeKp);

        double power = mCals.autoDriveMaxPwr;
        double distFromStart = tgtY - deltaY;
        double startPwr = ((power - mCals.autoDriveStartPwr)/(mCals.autoDriveStartDist)) * distFromStart 
            + mCals.autoDriveStartPwr;
        double endPwr = ((power - mCals.autoDriveEndPwr)/(mCals.autoDriveEndDist)) * errorY + mCals.autoDriveEndPwr;
        power = Util.min(power, startPwr, endPwr);

        m_subsystem.m_drivetrain.drive(strafe, -errorRot * mCals.autoDriveAngKp, 0, 0, true, power);

        SmartDashboard.putNumber("AutoXerr",errorX);
        SmartDashboard.putNumber("AutoYerr",errorY);
        SmartDashboard.putNumber("AutoAngerr",errorRot);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_drivetrain.drive(new Vector(0, 0), 0, 0, 0, true);
    }

    @Override
    public boolean isFinished(){
        if(Math.abs(errorX) < mCals.autoDriveStrafeRange && Math.abs(errorY) < 
        mCals.autoDriveStrafeRange && Math.abs(errorRot) < mCals.autoDriveAngRange){
            return true;
        } else return false;
    }
}