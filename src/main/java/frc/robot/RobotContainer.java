// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.commands.DriveCartesianCommand;
import frc.robot.commands.DriveSetDistanceCommand;
import frc.robot.subsystems.ClimbSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.commands.AngleShooterCommand;
import frc.robot.commands.ClimbCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.SetClimbPositionCommand;
import frc.robot.commands.ShooterPIDCommand;
import frc.robot.commands.SwapCommand;
import frc.robot.commands.WindowMotorsTestCommand;
import frc.robot.subsystems.RangefinderSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.WindowMotorSubsystem;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import static frc.robot.Constants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  
  //Xbox Controller
  private final XboxController xboxController = new XboxController(0);
  
  //Subsystems
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final RangefinderSubsystem m_rangefinderSubsystem = new RangefinderSubsystem();
  private final WindowMotorSubsystem m_windowMotorSubsystem = new WindowMotorSubsystem();
  private final ClimbSubsystem m_climbSubsystem = new ClimbSubsystem();

  //Commands
  private final AngleShooterCommand m_intakePositionCommand = new AngleShooterCommand(m_windowMotorSubsystem, -13);
  private final AngleShooterCommand m_defaultPositionCommand = new AngleShooterCommand(m_windowMotorSubsystem, 80);
  //private final AngleShooterCommand m_velocityTestCommand = new AngleShooterCommand(m_winchSubsystem, 45);

  private final IntakeCommand m_intakeCommand = new IntakeCommand(m_shooterSubsystem);
  //private final IntakeToggleCommand m_intakeToggle = new IntakeToggleCommand(m_intakeCommand, m_defaultPositionCommand);
  private final ShooterPIDCommand m_shooterPID = new ShooterPIDCommand(m_shooterSubsystem);

  private final WindowMotorsTestCommand m_windowTest = new WindowMotorsTestCommand(
    m_windowMotorSubsystem, () -> xboxController.getLeftTriggerAxis(), () -> xboxController.getRightTriggerAxis());

  private final ClimbCommand m_climbCommand = new ClimbCommand(
    m_climbSubsystem, () -> xboxController.getLeftY(), () -> -xboxController.getRightY());

  private final SetClimbPositionCommand m_midBarPosition = new SetClimbPositionCommand(m_climbSubsystem, MAX_CLIMB_POS);
  private final SetClimbPositionCommand m_downPosition = new SetClimbPositionCommand(m_climbSubsystem, 5);

  private final DriveCartesianCommand m_driveCommand = new DriveCartesianCommand(
    () -> xboxController.getLeftX(),
    () -> -xboxController.getLeftY(),
    () -> xboxController.getRightX(), m_driveSubsystem, 0.5);

  private final SwapCommand m_swapCommand = new SwapCommand(m_driveCommand, m_climbCommand);

  double optimalAngle = 15;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    CameraServer.startAutomaticCapture();

    Shuffleboard.getTab("SmartDashboard").addNumber("Optimal Angle", () -> optimalAngle);
    Shuffleboard.getTab("SmartDashboard").addNumber("Left Trigger", () -> xboxController.getLeftTriggerAxis());
    Shuffleboard.getTab("SmartDashboard").addNumber("Right Trigger", () -> xboxController.getRightTriggerAxis());

    AngleShooterCommand.setController(xboxController);
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
    shooterPositionButton.whenPressed(() -> {
      //optimalAngle = m_windowMotorSubsystem.getMaxAngle(m_rangefinderSubsystem.getDistance() + 2.8, 8, 28);
      double max = m_windowMotorSubsystem.getMaxAngle(m_rangefinderSubsystem.getDistance() + 1, 8.4667, 28);
      //double min = m_windowMotorSubsystem.getMinAngle(m_rangefinderSubsystem.getDistance() + 1, 8.667, 28);

      optimalAngle = max;

      new AngleShooterCommand(m_windowMotorSubsystem, optimalAngle).schedule();
    });

    Button defaultPositionButton = new Button(() -> xboxController.getYButton());
    defaultPositionButton.toggleWhenPressed(m_defaultPositionCommand);

    Button testPositionButton = new Button(() -> xboxController.getXButton());
    testPositionButton.whenPressed(m_windowTest);

    Button shootButton = new Button(() -> xboxController.getRightBumper());
    shootButton.whenPressed(m_shooterPID);

    Button intakeButton = new Button(() -> xboxController.getLeftBumper());
    intakeButton.toggleWhenPressed(m_intakeCommand);

    Button midBarButton = new Button(() -> xboxController.getLeftStickButton());
    midBarButton.whenPressed(m_midBarPosition);

    Button downButton = new Button(() -> xboxController.getRightStickButton());
    downButton.whenPressed(m_downPosition);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public SequentialCommandGroup getAutonomousCommand() {
    return new DriveSetDistanceCommand(m_driveSubsystem, -60).andThen(
      new AngleShooterCommand(m_windowMotorSubsystem, 74)).andThen(
        new WaitCommand(0.75).andThen(
          new ShooterPIDCommand(m_shooterSubsystem)).andThen(
            new DriveSetDistanceCommand(m_driveSubsystem, -20).andThen(
              new AngleShooterCommand(m_windowMotorSubsystem, -13))));
  }
}
