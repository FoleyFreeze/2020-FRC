package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class AutoDrive extends CommandBase{

    private RobotContainer m_subsystem;
    public double m_distance;
    public double m_direction;
    public double[] m_start;
    public double m_power;

    public AutoDrive(RobotContainer subsystem, double distance, double direction, double power){
        m_subsystem = subsystem;
        m_distance = distance;
        m_direction = direction;
        m_power = power;
    }

    @Override
    public void initialize(){
        m_start = m_subsystem.m_drivetrain.getDist();

    }

    @Override
    public void execute(){
        Vector strafe = new Vector(m_power, m_direction);
        m_subsystem.m_drivetrain.drive(strafe, 0, 0, 0, true);
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        double[] dists = m_subsystem.m_drivetrain.getDist();
        double diff = 0;
        for(int i = 0 ; i < dists.length ; i++){
            diff += Math.abs(dists[i] - m_start[i]);
        }
        return diff / 4 > m_distance;
    }
}