// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static frc.robot.Constants.*;

/** An example command that uses an example subsystem. */
public class IntakeCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

  private final ShooterSubsystem m_subsystem;
  private final Timer m_backDelay = new Timer();
  private final Timer m_frontDelay = new Timer();

  /**
   * Creates a new IntakeCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public IntakeCommand(ShooterSubsystem subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_backDelay.stop();
    m_backDelay.reset();

    m_frontDelay.stop();
    m_frontDelay.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_subsystem.getBackSensorActive()) m_backDelay.start();

    if(m_backDelay.hasElapsed(0.2)) m_subsystem.setIntakeMotors(0);
    else m_subsystem.setIntakeMotors(-INTAKE_POWER);

    if (m_subsystem.getFrontSensorActive()) m_frontDelay.start();

    m_subsystem.setShooterMotors(-INTAKE_POWER / 3f);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.setIntakeMotors(0);
    m_subsystem.setShooterMotors(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
