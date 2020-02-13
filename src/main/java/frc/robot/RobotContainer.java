/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.cals.CWheelCals;
import frc.robot.cals.CannonCals;
import frc.robot.cals.ClimberCals;
import frc.robot.cals.DisplayCals;
import frc.robot.cals.DriverCals;
import frc.robot.cals.InputCals;
import frc.robot.commands.Climb;
import frc.robot.commands.JoystickDrive;
import frc.robot.subsystems.*;
import frc.robot.cals.IntakeCals;
import frc.robot.cals.PneumaticsCals;
import frc.robot.cals.TransporterCals;
import frc.robot.cals.VisionCals;
import frc.robot.commands.AutoGather;
import frc.robot.commands.ZeroReset;
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
  public final Drivetrain m_drivetrain = new Drivetrain(new DriverCals());
  public final Intake m_intake = new Intake(new IntakeCals());

  public final Inputs m_input = new Inputs(new InputCals());
  public final JoystickDrive m_JoystickDrive = new JoystickDrive(this);

  public CannonClimber m_cannonClimber = new CannonClimber(new CannonCals(), new ClimberCals());
  public ColorWheel m_colorwheel = new ColorWheel(new CWheelCals());
  public Display m_display = new Display(new DisplayCals());
  public Pneumatics m_pneumatics = new Pneumatics(new PneumaticsCals());
  public Transporter m_transporter = new Transporter(new TransporterCals(), this);
  public Vision m_vision = new Vision(new VisionCals()); 
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
    CommandScheduler.getInstance().registerSubsystem(m_intake);
    
    m_intake.setDefaultCommand(new AutoGather(this, 0.0, m_input.autoGather()));
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
    m_input.intakeF.whileActiveOnce(new AutoGather(this, 
      IntakeCals.forwardPower, m_input.autoGather()));
    m_input.intakeR.whileActiveOnce(new AutoGather(this, 
      IntakeCals.backwardPower, m_input.autoGather()));
    m_input.angleReset.whileActiveOnce(new ZeroReset(this));
    m_input.climbUp.whileActiveOnce(new Climb(this, ClimberCals.upPower));
    m_input.climbDn.whileActiveOnce(new Climb(this, ClimberCals.dnPower));
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
