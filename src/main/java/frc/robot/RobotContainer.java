// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.DriveCartesianCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.AngleShooterCommand;
import frc.robot.commands.CalibrateClimbCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.commands.SwapCommand;
import frc.robot.commands.WinchTestCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.RangefinderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.WinchSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final XboxController xboxController = new XboxController(0);

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final RangefinderSubsystem m_rangefinderSubsystem = new RangefinderSubsystem();
  private final WinchSubsystem m_winchSubsystem = new WinchSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  private final IntakeCommand m_intakeCommand = new IntakeCommand(m_shooterSubsystem);
  private final ShooterPIDCommand m_shooterPID = new ShooterPIDCommand(m_shooterSubsystem);

  private final AngleShooterCommand m_intakePositionCommand = new AngleShooterCommand(m_winchSubsystem, 97, xboxController);
  private final AngleShooterCommand m_defaultPositionCommand = new AngleShooterCommand(m_winchSubsystem, 15, xboxController);
  private final AngleShooterCommand m_velocityTestCommand = new AngleShooterCommand(m_winchSubsystem, 45, xboxController);
  private final WinchTestCommand m_winchTest = new WinchTestCommand(
    m_winchSubsystem, () -> xboxController.getLeftTriggerAxis(), () -> xboxController.getRightTriggerAxis());

  private final ClimbCommand m_climbCommand = new ClimbCommand(
    m_climbSubsystem, () -> xboxController.getLeftY(), () -> -xboxController.getRightY());

  private final DriveCartesianCommand m_driveCommand = new DriveCartesianCommand(
    () -> xboxController.getLeftX(),
    () -> -xboxController.getLeftY(),
    () -> xboxController.getRightX(), m_driveSubsystem, 0.5);

  private final SwapCommand m_swapCommand = new SwapCommand(m_driveCommand, m_climbCommand);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    CameraServer.startAutomaticCapture();

    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    Button swapButton = new Button(() -> xboxController.getStartButton());
    swapButton.whenPressed(m_swapCommand);

    /*Button the = new Button(() -> xboxController.getBackButton());
    the.whenPressed(() -> System.out.println("George"));*/

    Button intakePositionButton = new Button(() -> xboxController.getBButton());
    intakePositionButton.whenPressed(m_intakePositionCommand);

    Button shooterPositionButton = new Button(() -> xboxController.getAButton());
    shooterPositionButton.whenPressed(new AngleShooterCommand(
      m_winchSubsystem, m_rangefinderSubsystem, m_shooterSubsystem, xboxController));

    Button winchTestButton = new Button(() -> xboxController.getYButton());
    winchTestButton.toggleWhenPressed(m_defaultPositionCommand);

    Button testPositionButton = new Button(() -> xboxController.getXButton());
    testPositionButton.whenPressed(m_velocityTestCommand);

    Button shootButton = new Button(() -> xboxController.getRightBumper());
    shootButton.whenPressed(m_shooterPID);

    Button intakeButton = new Button(() -> xboxController.getLeftBumper());
    intakeButton.toggleWhenPressed(m_intakeCommand);
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
