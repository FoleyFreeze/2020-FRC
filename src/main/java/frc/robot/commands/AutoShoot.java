package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CannonCals;
import frc.robot.subsystems.Vision.VisionData;

public class AutoShoot extends CommandBase{

    private RobotContainer m_subsystem;
    private CannonCals m_cals;
    public double rotCam = 0.0;
    
    public AutoShoot(RobotContainer subsystem){
        m_subsystem = subsystem;
        m_cals = m_subsystem.m_cannonClimber.shootCals;

        addRequirements(m_subsystem.m_drivetrain);
        addRequirements(m_subsystem.m_cannonClimber);
        addRequirements(m_subsystem.m_transporterCW);
    }

    @Override
    public void initialize(){
        if(m_subsystem.m_input.cam()){
            m_subsystem.m_cannonClimber.setCamLights(true);
        }
    }

    @Override
    public void execute(){
        double rot;
        double error;
        boolean aligned = false;
        double dist;
        if(m_subsystem.m_vision.hasTargetImage() && m_subsystem.m_input.cam()){
            VisionData image = m_subsystem.m_vision.targetData.getFirst();
            rotCam = image.robotangle + image.angle + m_subsystem.m_cannonClimber.shootCals.initJogAng;

            dist = image.dist;

            if(m_subsystem.m_input.twoVThree()){
                double angOfTgt = 180 - rotCam;
                dist = Math.sqrt(dist*dist + 29.25*29.25 - 2 * dist * 29.25 * Math.cos(angOfTgt));

                rotCam = Math.asin(29.25/dist * Math.sin(angOfTgt));
            }

            error = rotCam - m_subsystem.m_drivetrain.robotAng;
            error %= 360;
            if(error > 180) error -=360;
            else if(error < -180) error +=360;
            rot = error * m_cals.kPDrive;
        }else {
            rot = m_subsystem.m_input.getRot();
            error = 0;
            if(m_subsystem.m_input.layup()){
                dist = m_cals.layupDist;
            } else dist = m_cals.trenchDist;
        }
        if(error <= m_cals.tolerance) aligned = true;//make dependent on dist
        
        double maxPwr = m_cals.maxMovePwr;
        if(m_subsystem.m_input.shift()){
            dist *= 0.2;
            maxPwr *= 0.2;
        }

        m_subsystem.m_drivetrain.drive(m_subsystem.m_input.getXY(), rot, 0, 0, 
            m_subsystem.m_input.fieldOrient(), maxPwr);
        
        m_subsystem.m_cannonClimber.prime(dist);

        if(m_subsystem.m_cannonClimber.ready() && aligned) m_subsystem.m_transporterCW.shootAll();
        else m_subsystem.m_transporterCW.stoprot();


    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_cannonClimber.setpower(0);
        m_subsystem.m_cannonClimber.setCamLights(false);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}