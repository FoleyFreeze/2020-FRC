package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class TrenchRun extends CommandBase{

    private RobotContainer m_subsystem;
    private double wallDist;
    public double targetAngle;
    public enum Orientation{
        GATHERER, CLIMBER, AUTO
    }
    public Orientation orient;

    public TrenchRun(RobotContainer subsystem, Orientation orient){
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
        double distDiff = (/*current distance from sensors*/ - wallDist);
        double distPower = m_subsystem.m_drivetrain.k.trenchRunDistKp * distDiff;
        double angDiff = targetAngle - m_subsystem.m_drivetrain.navX.getAngle();
        if(Math.abs(angDiff) > 180){
            if(angDiff > 0){
                angDiff -= 360;
            } else angDiff += 360;
        }
        double angPower = m_subsystem.m_drivetrain.k.trenchRunAngKp * angDiff;

        Vector forward = Vector.fromXY(distPower, m_subsystem.m_input.getY());
        m_subsystem.m_drivetrain.drive(forward, angPower, 0, 0, true);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return true;
    }
}