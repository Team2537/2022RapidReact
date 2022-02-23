package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WinchSubsystem;

public class AngleShooterCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final WinchSubsystem m_subsystem;
    private final double m_angle;
  
    /**
     * Creates a new AngleShooterCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AngleShooterCommand(WinchSubsystem subsystem, double angle) {
      m_subsystem = subsystem;
      m_angle = angle;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      m_subsystem.setWinchAngle(m_angle);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      m_subsystem.setWinch(0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_angle - 0.05 < m_subsystem.getShooterAngle() && m_subsystem.getShooterAngle() < m_angle + 0.05;
    }
}
