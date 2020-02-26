package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CannonCals;
import frc.robot.subsystems.Vision.VisionData;

public class AutoShoot extends CommandBase{

    private RobotContainer m_subsystem;
    private CannonCals m_cals;
    public double rotCam = 0.0;
    public boolean auton;
    public double shootFinTime;
    double prevRobotAngle;
    double prevTime;
    
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
            m_subsystem.m_vision.NTEnablePiTgt(true);
        }
        
        auton = DriverStation.getInstance().isAutonomous();
        prevRobotAngle = m_subsystem.m_drivetrain.robotAng;
        prevTime = 0;
    }

    @Override
    public void execute(){
        double time = Timer.getFPGATimestamp();
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

            double robotAngle = m_subsystem.m_drivetrain.robotAng;
            error = rotCam - robotAngle;
            error %= 360;
            if(error > 180) error -=360;
            else if(error < -180) error +=360;
            
            double deltaAngle = robotAngle - prevRobotAngle;
            deltaAngle %= 360;
            if(deltaAngle > 180) deltaAngle -=360;
            else if(deltaAngle < -180) deltaAngle +=360;

            double d = (deltaAngle)/(time - prevTime);
            rot = error * m_cals.kPDrive - d * m_cals.kDDrive;

            if(rot > m_cals.maxRot) rot = m_cals.maxRot;
            else if(rot < -m_cals.maxRot) rot = -m_cals.maxRot;

        }else {
            rot = m_subsystem.m_input.getRot();
            error = 0;
            if(m_subsystem.m_input.layup()){
                dist = m_cals.layupDist;
            } else dist = m_cals.trenchDist;
        }
        if(Math.abs(error) <= m_cals.tolerance) aligned = true;//make dependent on dist
        
        m_subsystem.m_drivetrain.drive(m_subsystem.m_input.getXY(), rot, 0, 0, 
            m_subsystem.m_input.fieldOrient());
        
        m_subsystem.m_cannonClimber.prime(dist);

        if(m_subsystem.m_cannonClimber.ready() && aligned && m_subsystem.m_transporterCW.ballnumber > 0){
            m_subsystem.m_transporterCW.shootAll();
            shootFinTime = Timer.getFPGATimestamp() + m_subsystem.m_cannonClimber.shootCals.shootTime;
        } 
        else m_subsystem.m_transporterCW.stoprot();

        prevRobotAngle = m_subsystem.m_drivetrain.robotAng;
        prevTime = time;
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_cannonClimber.setpower(0);
        m_subsystem.m_cannonClimber.setCamLights(false);
        m_subsystem.m_vision.NTEnablePiTgt(false);
    }

    @Override
    public boolean isFinished(){
        if(auton) return Timer.getFPGATimestamp() >= shootFinTime && m_subsystem.m_transporterCW.ballnumber == 0;
        return false;
    }
}