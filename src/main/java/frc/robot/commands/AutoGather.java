package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.util.Vector;

public class AutoGather extends CommandBase {

    private RobotContainer m_subsystem;
    private double m_power;
    private boolean m_activate;

    public AutoGather(RobotContainer subsystem, double power, boolean activate){
        m_subsystem = subsystem;
        addRequirements(m_subsystem.m_intake);
        addRequirements(m_subsystem.m_drivetrain);

        m_power = power;
        m_activate = activate;
    }

    @Override
    public void initialize(){

    }
    @Override
    public void execute(){
        m_subsystem.m_intake.depSol.set(m_activate);

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
            m_subsystem.m_intake.depSol.set(false);
            m_subsystem.m_intake.spinmotor.setSpeed(-m_power);
        }
        m_subsystem.m_intake.setPower(m_power);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_intake.depSol.set(false);
    }
    @Override
    public boolean isFinished(){
        return false;
    }
}