package frc.robot.subsystems;

import edu.wpi.first.wpilibj.geometry.Pose2d;

public class RobotState{

    public int ballnumber;
    public Pose2d drivePos;

    public RobotState(){

    }

    public double getYPos(){
        return drivePos.getTranslation().getY();
    }

    public double getXPos(){
        return drivePos.getTranslation().getX();
    }
}