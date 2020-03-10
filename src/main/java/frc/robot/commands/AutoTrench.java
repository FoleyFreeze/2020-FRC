package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;
import frc.robot.util.Util;
import frc.robot.util.Vector;
import frc.robot.util.DistanceSensors.DistData;

public class AutoTrench extends CommandBase{

    private Drivetrain mDrivetrain;
    private Inputs mInput;
    private double wallDist;
    public double targetAngle;
    public enum Orientation{
        GATHERER, CLIMBER, AUTO
    }
    public Orientation orient;

    public AutoTrench(Drivetrain drivetrain, Orientation orient, Inputs input){
        mDrivetrain = drivetrain;
        mInput = input;
        addRequirements(mDrivetrain);

        this.orient = orient;
    }

    @Override
    public void initialize(){
        switch(orient){
            case GATHERER:
                targetAngle = 180;
                wallDist = 6.75;
                break;
            case CLIMBER:
                targetAngle = 90;
                wallDist = 8.5;
                break;
            case AUTO:
                if(180 - Math.abs(mDrivetrain.navX.getAngle()) 
                    < 90 - Math.abs(mDrivetrain.navX.getAngle())){
                    targetAngle = 180;
                    wallDist = 6.75;
                    break;
                }else{
                    targetAngle = 90;
                    wallDist = 8.5;
                    break;
                }
        }
    }

    @Override
    public void execute(){
        DistData dist;

        switch(orient){
            case GATHERER:
                dist = mDrivetrain.getRearDist();
                break;
            case CLIMBER:
                dist = mDrivetrain.getRightDist();
                break;
            case AUTO:
                if(targetAngle == 90) dist = mDrivetrain.getRightDist();
                else dist = mDrivetrain.getRearDist();
                break;
            default: //shouldnt happen
                dist = mDrivetrain.getRearDist();
        }

        double distDiff = (dist.dist - wallDist);
        double distPower = mDrivetrain.k.trenchRunDistKp * distDiff;
        double angDiff = Util.angleDiff(targetAngle, mDrivetrain.robotAng);
        double angPower = mDrivetrain.k.trenchRunAngKp * angDiff;

        //limit PID powers to the cal value
        if(Math.abs(angPower) > mDrivetrain.k.trenchRunMaxSpd){
            if(angPower > 0) angPower = mDrivetrain.k.trenchRunMaxSpd;
            else if(angPower < 0) angPower = -mDrivetrain.k.trenchRunMaxSpd;
        }

        if(Math.abs(distPower) > mDrivetrain.k.trenchRunMaxSpd){
            if(distPower > 0) distPower = mDrivetrain.k.trenchRunMaxSpd;
            else if(distPower < 0) distPower = -mDrivetrain.k.trenchRunMaxSpd;
        }

        Vector forward = Vector.fromXY(distPower, mInput.getY());

        mDrivetrain.drive(forward, angPower, 0, 0, true);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}