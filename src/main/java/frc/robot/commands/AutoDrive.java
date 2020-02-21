package frc.robot.commands;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DriverCals;
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
    private Pose2d pose;
    private DriverCals mCals = m_subsystem.m_drivetrain.k;

    public AutoDrive(RobotContainer subsystem, double deltaX, double deltaY, double angle, boolean deltaVsField){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.tgtRot = angle;
        this.deltaVsField = deltaVsField;

        pose = m_subsystem.m_drivetrain.drivePos;
    }

    @Override
    public void initialize(){
        if(deltaVsField){
            tgtX = pose.getTranslation().getX() + deltaX;
            tgtY = pose.getTranslation().getY() + deltaY;
        } else {
            tgtX = deltaX;
            tgtY = deltaY;
        }
    }

    @Override
    public void execute(){
        errorX = (tgtX - pose.getTranslation().getX()) * mCals.autoDriveStrafeKp;
        errorY = (tgtY - pose.getTranslation().getY()) * mCals.autoDriveStrafeKp;
        errorRot = (Math.toRadians(tgtRot) - pose.getRotation().getRadians()) * mCals.autoDriveAngKp;

        Vector strafe = Vector.fromXY(errorX, errorY);
        m_subsystem.m_drivetrain.drive(strafe, errorRot, 0, 0, true, 0.2);
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