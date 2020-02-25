package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.DriverCals;
import frc.robot.util.Vector;
import frc.robot.util.DistanceSensors.DistData;

public class AutoTrench extends CommandBase{

    private RobotContainer m_subsystem;
    private DriverCals m_cals = m_subsystem.m_drivetrain.k;
    private double wallDist;
    public double targetAngle;
    public enum Orientation{
        GATHERER, CLIMBER, AUTO
    }
    public Orientation orient;

    public AutoTrench(RobotContainer subsystem, Orientation orient){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_drivetrain);

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
                if(180 - Math.abs(m_subsystem.m_drivetrain.navX.getAngle()) 
                    < 90 - Math.abs(m_subsystem.m_drivetrain.navX.getAngle())){
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
                dist = m_subsystem.m_drivetrain.getRearDist();
                break;
            case CLIMBER:
                dist = m_subsystem.m_drivetrain.getRightDist();
                break;
            case AUTO:
                if(targetAngle == 90) dist = m_subsystem.m_drivetrain.getRightDist();
                else dist = m_subsystem.m_drivetrain.getRearDist();
                break;
            default: //shouldnt happen
                dist = m_subsystem.m_drivetrain.getRearDist();
        }

        double distDiff = (dist.dist - wallDist);
        double distPower = m_cals.trenchRunDistKp * distDiff;
        double angDiff = targetAngle - m_subsystem.m_drivetrain.navX.getAngle();
        if(Math.abs(angDiff) > 180){
            if(angDiff > 0){
                angDiff -= 360;
            } else angDiff += 360;
        }
        double angPower = m_cals.trenchRunAngKp * angDiff;

        Vector forward = Vector.fromXY(distPower, m_subsystem.m_input.getY());
        double maxPwr = m_cals.trenchRunMaxSpd;
        if(m_subsystem.m_input.shift()){
            maxPwr *= 0.2;
        }
        m_subsystem.m_drivetrain.drive(forward, angPower, 0, 0, true, maxPwr);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}