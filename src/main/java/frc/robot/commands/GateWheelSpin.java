package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.TransporterCW;

public class GateWheelSpin extends CommandBase{
    
    TransporterCW m_TCW;
    
    public GateWheelSpin(TransporterCW TCW){
        m_TCW = TCW;
        if(TCW.tCals.disabled) return;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_TCW.spinGate(m_TCW.tCals.TN_LOADSPEED);
    }

    @Override
    public void end(boolean interrupted){
        m_TCW.spinGate(m_TCW.tCals.TN_STOPSPEED);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}