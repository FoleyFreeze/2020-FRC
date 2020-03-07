package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.TransporterCW;

public class CWCombo extends SequentialCommandGroup{
    
    TransporterCW mColorWheel;
    Inputs mInput;

    public CWCombo(TransporterCW colorwheel, Inputs input){
        mColorWheel = colorwheel;
        mInput = input;

        addCommands(new CWInit(colorwheel));
        addCommands(new CWSpin(colorwheel, input));
        addCommands(new CWStop(colorwheel));
        addCommands(new CWMove(colorwheel));
    }
}