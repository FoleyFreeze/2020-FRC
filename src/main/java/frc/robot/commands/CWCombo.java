package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GateCW;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.RobotState;

public class CWCombo extends SequentialCommandGroup{
    
    GateCW mColorWheel;
    RobotState mState;
    Inputs mInput;
    Drivetrain mDrivetrain;

    public CWCombo(GateCW colorwheel, Inputs input, RobotState state, Drivetrain drivetrain){
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