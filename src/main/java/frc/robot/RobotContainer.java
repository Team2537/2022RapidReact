// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.StrafeCommand;
import frc.robot.commands.TurnCommand;
import frc.robot.commands.TurnConcerningCommand;
import frc.robot.commands.TurnRearCommand;
import frc.robot.commands.DiagonalCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final DriveCommand m_driveCommand = new DriveCommand(m_driveSubsystem);
  private final TurnCommand m_turnCommand = new TurnCommand(m_driveSubsystem);
  private final DiagonalCommand m_diagonalCommand = new DiagonalCommand(m_driveSubsystem);
  private final StrafeCommand m_strafeCommand = new StrafeCommand(m_driveSubsystem);
  private final TurnConcerningCommand m_turnConcerningCommand = new TurnConcerningCommand(m_driveSubsystem);
  private final TurnRearCommand m_turnRearCommand = new TurnRearCommand(m_driveSubsystem);
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }

  public Command getDriveCommand() {
    return m_driveCommand;
  }

  public Command getTurnCommand() {
    return m_turnCommand;
  }

  public Command getDiagonalCommand() {
    return m_diagonalCommand;
  }

  public Command getStrafeCommand() {
    return m_strafeCommand;
  }
  
  public Command getTurnConcerningCommand() {
    return m_turnConcerningCommand;
  }

  public Command getTurnRearCommand() {
    return m_turnRearCommand;
  }

}
