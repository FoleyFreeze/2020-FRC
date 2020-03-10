package frc.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class AutoGatherOLD extends CommandBase {

    private RobotContainer m_subsystem;
    private boolean auton;

    public AutoGatherOLD(RobotContainer subsystem){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_intake);
        addRequirements(m_subsystem.m_drivetrain);
    }

    @Override
    public void initialize(){
        auton = DriverStation.getInstance().isAutonomous();
        m_subsystem.m_vision.NTEnablePiBall(true);
        m_subsystem.m_intake.dropIntake(true);
    }

    @Override
    public void execute(){
        if(m_subsystem.m_input.enableBallCam() && m_subsystem.m_vision.hasBallImage()){//robot has control

            Vector strafe = Vector.fromXY(0, m_subsystem.m_vision.ballData.element().dist * 
                m_subsystem.m_drivetrain.k.trenchRunDistKp);

            double rot = m_subsystem.m_vision.ballData.element().angle -
                 m_subsystem.m_drivetrain.navX.getAngle() * m_subsystem.m_drivetrain.k.trenchRunAngKp;

            m_subsystem.m_drivetrain.drive(strafe, rot, 0, 0, m_subsystem.m_input.fieldOrient());
        }else{//driver has control
            Vector strafe = Vector.fromXY(m_subsystem.m_input.getX(), m_subsystem.m_input.getY());

            double rot = m_subsystem.m_input.getRot();

            m_subsystem.m_drivetrain.drive(strafe, rot, 0, 0, m_subsystem.m_input.fieldOrient());
        }
        
        if(m_subsystem.m_transporterCW.ballnumber >= 5 && !m_subsystem.m_input.shift()){
            m_subsystem.m_intake.dropIntake(false);
            m_subsystem.m_intake.setPower(m_subsystem.m_intake.k.backwardPower);
        } else if(m_subsystem.m_transporterCW.isIndexing()){
            m_subsystem.m_intake.setPower(m_subsystem.m_intake.k.idxPower);
        } else {
            m_subsystem.m_intake.setPower(m_subsystem.m_intake.k.forwardPower);
        }
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_intake.dropIntake(false);
        m_subsystem.m_intake.setPower(0);
        m_subsystem.m_vision.NTEnablePiBall(false);
    }
    
    @Override
    public boolean isFinished(){
        if(auton){
            return m_subsystem.m_transporterCW.ballnumber >= 5;
        }
        return false;
    }
}