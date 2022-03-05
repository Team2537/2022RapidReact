// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.WinchSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final ShooterSubsystem m_subsystem;
  private final WinchSubsystem m_winchSubsystem;
  private final Timer m_timer = new Timer();

  /**
   * Creates a new IntakeCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeCommand(ShooterSubsystem subsystem, WinchSubsystem winchSubsystem) {
    m_subsystem = subsystem;
    m_winchSubsystem = winchSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem, winchSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_timer.stop();
    m_timer.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_subsystem.getBackSensorActive()) m_timer.start();

    if(m_timer.hasElapsed(0.1)) m_subsystem.setIntakeMotors(0);
    else m_subsystem.setIntakeMotors(-INTAKE_POWER);
    m_subsystem.setShooterMotors(-INTAKE_POWER / 3f);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setIntakeMotors(0);
    m_subsystem.setShooterMotors(0);

    if (m_subsystem.getBackSensorActive() && m_subsystem.getFrontSensorActive()) {
      new AngleShooterCommand(m_winchSubsystem, 15).schedule();
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_subsystem.getBackSensorActive() && m_subsystem.getFrontSensorActive();
  }
}
