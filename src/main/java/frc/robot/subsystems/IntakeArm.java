package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.cals.IntakeArmCals;

public class IntakeArm extends SubsystemBase{
    
    private Solenoid dropArm;
    public IntakeArmCals m_Cals;

    public IntakeArm(IntakeArmCals cals){
        m_Cals = cals;
        if(m_Cals.disabled) return;
        dropArm = new Solenoid(m_Cals.armSol);
    }

    public void deploy(boolean on){
        if(m_Cals.disabled) return;
        dropArm.set(on);
    }

    public boolean isOut(){
        if(m_Cals.disabled) return false;
        return dropArm.get();
    }
}