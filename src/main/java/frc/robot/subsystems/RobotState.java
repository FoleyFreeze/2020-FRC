package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class RobotState{

    public int ballnumber;
    public Pose2d drivePos;
    public AHRS navX;
    public double robotAng;

    public RobotState(){
        navX = new AHRS();
    }

    public double robotAng(){
        robotAng = navX.getAngle() % 360;
        return robotAng;
    }

    public double getYPos(){
        return drivePos.getTranslation().getY();
    }

    public double getXPos(){
        return drivePos.getTranslation().getX();
    }
}