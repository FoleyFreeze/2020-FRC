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
import frc.robot.cals.DriverCals;
import frc.robot.cals.ElectroKendro;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.ManualIntake;
import frc.robot.commands.ManualRevolve;
import frc.robot.commands.ManualShoot;
import frc.robot.commands.TrenchRun;
import frc.robot.subsystems.*;
import frc.robot.cals.IntakeCals;
import frc.robot.cals.PneumaticsCals;
import frc.robot.cals.TransporterCals;
import frc.robot.cals.VisionCals;
import frc.robot.commands.ZeroReset;
import frc.robot.commands.TrenchRun.Orientation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutonSquare;
import frc.robot.commands.Climb;
import frc.robot.commands.Jog;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final Drivetrain m_drivetrain = new Drivetrain(new DriverCals(), this);
  public final Intake m_intake = new Intake(new IntakeCals());

  public final Inputs m_input = new Inputs(new ElectroKendro());
  public final JoystickDrive m_JoystickDrive = new JoystickDrive(this);

  public CannonClimber m_cannonClimber = new CannonClimber(new CannonCals(), new ClimberCals());
  public Display m_display = new Display();
  public Pneumatics m_pneumatics = new Pneumatics(new PneumaticsCals());
  public TransporterCW m_transporterCW = new TransporterCW(new TransporterCals(), new CWheelCals(), this);
  public Vision m_vision = new Vision(new VisionCals());

  private SequentialCommandGroup autonCmds;
  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain);
    CommandScheduler.getInstance().registerSubsystem(m_intake);
    
    //m_intake.setDefaultCommand(new AutoGather(this, 0.0, m_input.autoGather()));
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
    m_input.shoot.whileActiveOnce(new AutoShoot(this));
    m_input.angleReset.whileActiveOnce(new ZeroReset(this));
    //m_input.climbUp.whileActiveOnce(new Climb(this, ClimberCals.upPower));
    //m_input.climbDn.whileActiveOnce(new Climb(this, ClimberCals.dnPower));
    //m_input.manualIntake.whileActiveOnce(new ManualIntake(this));
    m_input.manualShoot.whileActiveOnce(new ManualShoot(this));
    //m_input.revolve.whileActiveOnce(new ManualRevolve(this));
    //m_input.jogUp.whileActiveOnce(new Jog(this, true, true));
    //m_input.jogDn.whileActiveOnce(new Jog(this, true, false));
    //m_input.jogL.whileActiveOnce(new Jog(this, false, true));
    //m_input.jogR.whileActiveOnce(new Jog(this, false, false));
    //m_input.autoTrench.whileActiveOnce(new TrenchRun(this, Orientation.AUTO));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    autonCmds.addCommands(
      new AutonSquare(this)
    );
    return autonCmds;
  }
}
