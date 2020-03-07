package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.TransporterCW;
import frc.robot.util.Vector;

public class CWMove extends CommandBase{
    
    TransporterCW mColorWheel;
    Vector xy;
    double start;

    public CWMove(TransporterCW colorwheel){
        mColorWheel = colorwheel;
        addRequirements(mColorWheel);
        xy = Vector.fromXY(0, -1);
        start = m_subsystem.m_drivetrain.drivePos.getTranslation().getY();
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_drivetrain.drive(xy, 0);
    }

    @Override
    public void end(boolean interrupted){
        mColorWheel.deployCW(false);
    }

    @Override
    public boolean isFinished(){
        return m_subsystem.m_drivetrain.drivePos.getTranslation().getY() >= start - 3;
    }
}