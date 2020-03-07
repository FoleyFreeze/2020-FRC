package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.cals.CwTnCals;
import edu.wpi.first.wpilibj.util.Color;

public class CWColor extends CommandBase{

    RobotContainer m_subsystem;
    CwTnCals k;
    char tgtColor = m_subsystem.m_transporterCW.gameData.charAt(0);
    Color detectedColor = m_subsystem.m_transporterCW.detectedColor;

    public CWColor(RobotContainer subsystem, CwTnCals k){
        m_subsystem = subsystem;
        this.k = k;
    }

    @Override
    public void initialize(){

    }

    @Override
    public void execute(){
        m_subsystem.m_transporterCW.deployCW(true);
    }

    @Override
    public void end(boolean interrupted){
        m_subsystem.m_transporterCW.deployCW(false);
    }

    @Override
    public boolean isFinished(){
        
        switch(tgtColor){
            case 'B':
            return detectedColor == k.Red;//We see red, but it the detector sees blue

            case 'G':
            return detectedColor == k.Yellow;//We see yellow, but it the detector sees green

            case 'R':
            return detectedColor == k.Blue;//We see blue, but it the detector sees red

            case 'Y':
            return detectedColor == k.Green;//We see green, but it the detector sees yellow

            default:
            return false;
        }
    }
}