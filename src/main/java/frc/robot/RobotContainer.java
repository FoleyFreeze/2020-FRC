/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.cals.CwTnCals;
import frc.robot.cals.CannonClimbCals;
import frc.robot.cals.DriverCals;
import frc.robot.cals.ElectroKendro;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.ManualIntake;
import frc.robot.commands.ManualRevolve;
import frc.robot.commands.ManualShoot;
import frc.robot.commands.AutoTrench;
import frc.robot.subsystems.*;
import frc.robot.cals.IntakeCals;
import frc.robot.cals.PneumaticsCals;
import frc.robot.cals.VisionCals;
import frc.robot.commands.ZeroReset;
import frc.robot.commands.AutoTrench.Orientation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutonSquare;
import frc.robot.commands.CWCombo;
import frc.robot.commands.Climb;
import frc.robot.commands.Jog;


public class RobotContainer {

  public final RobotState m_state = new RobotState();
  public final Drivetrain m_drivetrain = new Drivetrain(new DriverCals(), this, m_state);
  public final Intake m_intake = new Intake(new IntakeCals());
  public final Inputs m_input = new Inputs(new ElectroKendro());
  public final CannonClimber m_cannonClimber = new CannonClimber(new CannonClimbCals());
  public final Display m_display = new Display();
  public final Pneumatics m_pneumatics = new Pneumatics(new PneumaticsCals());
  public final TransporterCW m_transporterCW = new TransporterCW(new CwTnCals(), this, m_state);
  public final Vision m_vision = new Vision(new VisionCals());

  
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain, m_intake, 
    m_cannonClimber, m_pneumatics, m_transporterCW);
    
    m_drivetrain.setDefaultCommand(new JoystickDrive(m_drivetrain, m_input));
    
      
    configureButtonBindings();
  }

  
  private void configureButtonBindings() {
    m_input.shoot.and(m_input.layupTrigger.negate()).whileActiveOnce(new AutoShoot(m_drivetrain, m_cannonClimber, m_transporterCW, m_input, m_vision, m_state));
    m_input.shoot.and(m_input.layupTrigger).whileActiveOnce(new SequentialCommandGroup(new AutoDrive(m_drivetrain, m_state, 0, -24, 0, true), new AutoShoot(m_drivetrain, m_cannonClimber, m_transporterCW, m_input, m_vision, m_state)));
    m_input.angleReset.whileActiveOnce(new ZeroReset(m_drivetrain));
    m_input.climbUp.whileActiveOnce(new Climb(m_cannonClimber, m_cannonClimber.k.upPower));
    m_input.climbDn.whileActiveOnce(new Climb(m_cannonClimber, m_cannonClimber.k.dnPower));
    m_input.manualIntake.whileActiveOnce(new ManualIntake(m_intake, m_input));
    m_input.manualShoot.whileActiveOnce(new ManualShoot(m_cannonClimber, m_input));
    m_input.revolve.whileActiveOnce(new ManualRevolve(m_transporterCW, m_input));
    m_input.jogUp.whileActiveOnce(new Jog(m_cannonClimber, true, true));
    m_input.jogDn.whileActiveOnce(new Jog(m_cannonClimber, true, false));
    m_input.jogL.whileActiveOnce(new Jog(m_cannonClimber, false, true));
    m_input.jogR.whileActiveOnce(new Jog(m_cannonClimber, false, false));
    m_input.autoTrench.whileActiveOnce(new AutoTrench(m_drivetrain, Orientation.AUTO, m_input));
    m_input.cwActivate.whileActiveOnce(new CWCombo(m_transporterCW, m_input, m_state, m_drivetrain));
  }

  public Command getAutonomousCommand() {

    return new AutonSquare(m_drivetrain, m_state);
  }
}
