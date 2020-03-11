package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.GateCW;

public class GateWheelSpin extends CommandBase{
    
    GateCW m_TCW;
    
    public GateWheelSpin(GateCW TCW){
        m_TCW = TCW;
        if(TCW.k.tnDisabled) return;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_TCW.spinGate(m_TCW.k.TN_LOADSPEED);
    }

    @Override
    public void end(boolean interrupted){
        m_TCW.spinGate(m_TCW.k.TN_STOPSPEED);
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}