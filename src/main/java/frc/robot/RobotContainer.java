/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.cals.DriverCals;
import frc.robot.commands.JoystickDrive;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Inputs;
import frc.robot.cals.IntakeCals;
import frc.robot.commands.JoystickIntake;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain(new DriverCals());
  private final Intake m_intake = new Intake(new IntakeCals());

  private final Inputs m_input = new Inputs();
  private final JoystickDrive m_JoystickDrive = new JoystickDrive(m_drivetrain, m_input);

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
    CommandScheduler.getInstance().registerSubsystem(m_intake);
    
    m_intake.setDefaultCommand(new JoystickIntake(m_intake, 0));
    m_drivetrain.setDefaultCommand(m_JoystickDrive);
    
    // Configure the button bindings    
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    m_input.intakeF.whileActiveOnce(new JoystickIntake(m_intake, 0.5));
    m_input.intakeR.whileActiveOnce(new JoystickIntake(m_intake, -0.5));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
