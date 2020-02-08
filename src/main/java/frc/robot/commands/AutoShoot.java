package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CannonCals;
import frc.robot.subsystems.Vision.VisionData;

public class AutoShoot extends CommandBase{

    private RobotContainer m_subsystem;
    private CannonCals m_cals;
    
    public AutoShoot(RobotContainer subsystem){
        m_subsystem = subsystem;
        m_cals = m_subsystem.m_cannon.mCals;

        addRequirements(m_subsystem.m_drivetrain);
        addRequirements(m_subsystem.m_cannon);
        addRequirements(m_subsystem.m_transporter);
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        double rot;
        double error;
        boolean aligned = false;
        double dist;
        if(m_subsystem.m_vision.hasTargetImage()){
            VisionData image = m_subsystem.m_vision.targetData.getFirst();
            double rotCam = image.robotangle + image.angle;
            error = rotCam - m_subsystem.m_drivetrain.robotAng;
            error %= 360;
            if(error > 180) error -=360;
            else if(error < -180) error +=360;
            rot = error * m_cals.kPDrive;

            dist = image.dist;
        }else {
            rot = m_subsystem.m_input.getRot();
            error = 0;
            dist = 12;//TODO make cals
        }
        if(error >= m_cals.tolerance) aligned = true;//make dependent on dist
        m_subsystem.m_drivetrain.drive(m_subsystem.m_input.getXY(), rot, 0, 0, m_subsystem.m_input.fieldOrient());
        m_subsystem.m_cannon.prime(dist);
        if(m_subsystem.m_cannon.ready() && aligned) m_subsystem.m_transporter.shootAll();
    }

    @Override
    public void end(boolean interrupted){

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}