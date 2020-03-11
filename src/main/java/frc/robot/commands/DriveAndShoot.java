package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.CannonClimber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.GateCW;
import frc.robot.subsystems.Inputs;
import frc.robot.subsystems.RobotState;
import frc.robot.subsystems.Vision;

public class DriveAndShoot extends SequentialCommandGroup{//TODO Test me!!!
    public DriveAndShoot(Drivetrain drivetrain, RobotState state, CannonClimber cannon, Inputs input, Vision vision, GateCW transporter){
        addCommands(new AutoDrive(drivetrain, state, 0, -24, 0, false));
        addCommands(new AutoShoot(drivetrain, cannon, transporter, input, vision, state));
    }
}