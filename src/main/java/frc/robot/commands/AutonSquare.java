package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class AutonSquare extends SequentialCommandGroup{

    private RobotContainer m_subsystem;

    public AutonSquare(RobotContainer subsystem){
        m_subsystem = subsystem;

        addCommands(
            new AutoDrive(subsystem, 48, 0, 0, true),
            new AutoDrive(subsystem, 0, 48, -90, true),
            new AutoDrive(subsystem, -48, 0, -180, true),
            new AutoDrive(subsystem, 0, -48, -270, true)
        );
    }
}