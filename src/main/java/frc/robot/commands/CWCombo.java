package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.TransporterCW;

public class CWCombo extends SequentialCommandGroup{
    
    TransporterCW mColorWheel;
    RobotState mState;
    Inputs mInput;
    Drivetrain mDrivetrain;

    public CWCombo(TransporterCW colorwheel, Inputs input, RobotState state, Drivetrain drivetrain){
        mColorWheel = colorwheel;
        mInput = input;
        mState = state;
        mDrivetrain = drivetrain;

        addCommands(new CWInit(colorwheel));
        addCommands(new CWSpin(colorwheel, input));
        addCommands(new CWStop(colorwheel));
        addCommands(new CWMove(colorwheel, drivetrain, state));
    }
}