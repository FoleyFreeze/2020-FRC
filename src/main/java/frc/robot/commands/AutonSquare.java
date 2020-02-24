package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;

public class AutonSquare extends SequentialCommandGroup{

    private RobotContainer m_subsystem;

    public AutonSquare(RobotContainer subsystem){
        m_subsystem = subsystem;

        addCommands(
            new AutoDrive(subsystem, 120, 0, -90, false),
            new AutoDrive(subsystem, 120, 120, 180, false),
            new AutoDrive(subsystem, 0, 120, 90, false),
            new AutoDrive(subsystem, 0, 0, 0, false)
        );
    }
}