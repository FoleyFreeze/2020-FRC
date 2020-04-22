package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RobotState;

public class AutonSquare extends SequentialCommandGroup{

    private Drivetrain mDrivetrain;
    private RobotState mState;

    public AutonSquare(Drivetrain subsystem, RobotState state){
        mDrivetrain = subsystem;
        mState = state;

        addCommands(
            new AutoDrive(mDrivetrain, mState, /*120, 0, -90, */false, new double[] {120, 120, 0, 0}, 
                new double[] {0, 120, 120, 0}, new double[] {-90, 180, 90, 0})
            /*new AutoDrive(mDrivetrain, mState, 120, 120, 180, false),
            new AutoDrive(mDrivetrain, mState, 0, 120, 90, false),
            new AutoDrive(mDrivetrain, mState, 0, 0, 0, false)*/
        );
    }
}