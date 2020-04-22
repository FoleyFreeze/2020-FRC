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
import frc.robot.cals.HoodCals;
import frc.robot.commands.JoystickDrive;
import frc.robot.commands.ManualIntake;
import frc.robot.commands.ManualRevolve;
import frc.robot.commands.ManualShoot;
import frc.robot.commands.SetHoodPos;
import frc.robot.commands.AutoTrench;
import frc.robot.subsystems.*;
import frc.robot.cals.IntakeCals;
import frc.robot.cals.PneumaticsCals;
import frc.robot.cals.TransporterCals;
import frc.robot.cals.VisionCals;
import frc.robot.commands.ZeroReset;
import frc.robot.commands.AutoTrench.Orientation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.AutoGather;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.AutonSquare;
import frc.robot.commands.CWCombo;
import frc.robot.commands.Climb;
import frc.robot.commands.DriveTime;
import frc.robot.commands.Jog;


public class RobotContainer {

  public final RobotState m_state = new RobotState();
  public final Drivetrain m_drivetrain = new Drivetrain(new DriverCals(), this, m_state);
  public final Intake m_intake = new Intake(new IntakeCals());
  public final Inputs m_input = new Inputs(new ElectroKendro());
  public final CannonClimber m_cannonClimber = new CannonClimber(new CannonClimbCals());
  public final Display m_display = new Display();
  public final Pneumatics m_pneumatics = new Pneumatics(new PneumaticsCals());
  public final GateCW m_transporterCW = new GateCW(new CwTnCals(), this, m_state);
  public final Vision m_vision = new Vision(new VisionCals());
  public final Hood m_Hood = new Hood(new HoodCals());
  public final Revolver m_Revolver = new Revolver(new TransporterCals(), m_state);

  public SendableChooser<CommandBase> autonChooser;
  
  public RobotContainer() {
    CommandScheduler.getInstance().registerSubsystem(m_drivetrain, m_intake, 
    m_cannonClimber, m_pneumatics, m_transporterCW);
    
    m_drivetrain.setDefaultCommand(new JoystickDrive(m_drivetrain, m_input));

    m_Hood.setDefaultCommand(new SetHoodPos(m_Hood));
      
    configureButtonBindings();

    autonChooser = new SendableChooser<>();
    autonChooser.addOption("DriveOnly", new AutoDrive(m_drivetrain, m_state, 0, -48, 0, true));
    //autonChooser.setDefaultOption("DriveAndShoot", new SequentialCommandGroup(new AutoShoot(this),new AutoDrive(this,0,-48,90,true)));
    autonChooser.setDefaultOption("DriveAndShoot", new SequentialCommandGroup(new AutoShoot(m_cannonClimber, m_Revolver, m_vision),new DriveTime(3, this, 0, -0.4, 0)));
    
    autonChooser.addOption("AutoSquare", new AutonSquare(m_drivetrain, m_state));
    SmartDashboard.putData(autonChooser);
  }

  
  private void configureButtonBindings() {
    m_input.shoot.and(m_input.layupTrigger.negate()).whileActiveOnce(new AutoShoot(m_cannonClimber, m_Revolver, m_vision));
    m_input.shoot.and(m_input.layupTrigger).whileActiveOnce(new SequentialCommandGroup(new AutoDrive(m_drivetrain, m_state, 0, -24, 0, true), new AutoShoot(m_cannonClimber, m_Revolver, m_vision)));
    m_input.angleReset.whileActiveOnce(new ZeroReset(m_drivetrain));
    m_input.climbUp.whileActiveOnce(new Climb(m_cannonClimber, m_cannonClimber.k.upPower, m_drivetrain, m_state));
    m_input.climbDn.whileActiveOnce(new Climb(m_cannonClimber, m_cannonClimber.k.dnPower, m_drivetrain, m_state));
    m_input.manualIntake.whileActiveOnce(new ManualIntake(m_intake, m_input));
    m_input.manualShoot.whileActiveOnce(new ManualShoot(m_cannonClimber, m_Revolver, m_Hood, m_input));
    m_input.revolve.whileActiveOnce(new ManualRevolve(m_Revolver, m_input));
    m_input.jogUp.whileActiveOnce(new Jog(m_cannonClimber, true, true));
    m_input.jogDn.whileActiveOnce(new Jog(m_cannonClimber, true, false));
    m_input.jogL.whileActiveOnce(new Jog(m_cannonClimber, false, true));
    m_input.jogR.whileActiveOnce(new Jog(m_cannonClimber, false, false));
    m_input.autoTrench.whileActiveOnce(new AutoTrench(m_drivetrain, Orientation.AUTO, m_input));
    m_input.cwActivate.whileActiveOnce(new CWCombo(m_transporterCW, m_input, m_state, m_drivetrain));
    m_input.gather.whileActiveOnce(new AutoGather(m_intake, m_Revolver, m_transporterCW, m_state));
  }

  public Command getAutonomousCommand() {
    return autonChooser.getSelected();
  }
}
