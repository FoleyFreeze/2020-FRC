package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.RobotState;

public class DriveOnly extends SequentialCommandGroup{//back up and spin in a circle
    public DriveOnly(Drivetrain drivetrain, RobotState state){
        addCommands(new AutoDrive(drivetrain, state, true, new double[] {0}, new double[] {-24}, new double[] {0}));//TODO test me!!!
    }
}