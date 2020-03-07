/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.cals.CwTnCals;
import frc.robot.cals.CannonCals;
import frc.robot.cals.ClimberCals;
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

  public final Drivetrain m_drivetrain = new Drivetrain(new DriverCals(), this);
  public final Intake m_intake = new Intake(new IntakeCals());
  public final Inputs m_input = new Inputs(new ElectroKendro());
  public final CannonClimber m_cannonClimber = new CannonClimber(new CannonCals(), new ClimberCals());
  public final Display m_display = new Display();
  public final Pneumatics m_pneumatics = new Pneumatics(new PneumaticsCals());
  public final TransporterCW m_transporterCW = new TransporterCW(new CwTnCals(), this);
  public final Vision m_vision = new Vision(new VisionCals());

  
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain, m_intake, 
    m_cannonClimber, m_pneumatics, m_transporterCW);
    
    m_drivetrain.setDefaultCommand(new JoystickDrive(this));
    
      
    configureButtonBindings();
  }

  
  private void configureButtonBindings() {
    m_input.shoot.and(m_input.layupTrigger.negate()).whileActiveOnce(new AutoShoot(this));
    m_input.shoot.and(m_input.layupTrigger).whileActiveOnce(new SequentialCommandGroup(new AutoDrive(this, 0, -24, 0, true), new AutoShoot(this)));
    m_input.angleReset.whileActiveOnce(new ZeroReset(this));
    m_input.climbUp.whileActiveOnce(new Climb(this, ClimberCals.upPower));
    m_input.climbDn.whileActiveOnce(new Climb(this, ClimberCals.dnPower));
    m_input.manualIntake.whileActiveOnce(new ManualIntake(this));
    m_input.manualShoot.whileActiveOnce(new ManualShoot(this));
    m_input.revolve.whileActiveOnce(new ManualRevolve(this));
    m_input.jogUp.whileActiveOnce(new Jog(this, true, true));
    m_input.jogDn.whileActiveOnce(new Jog(this, true, false));
    m_input.jogL.whileActiveOnce(new Jog(this, false, true));
    m_input.jogR.whileActiveOnce(new Jog(this, false, false));
    m_input.autoTrench.whileActiveOnce(new AutoTrench(this, Orientation.AUTO));
    m_input.cwActivate.whileActiveOnce(new CWCombo());
  }

  public Command getAutonomousCommand() {

    return new AutonSquare(this);
  }
}
