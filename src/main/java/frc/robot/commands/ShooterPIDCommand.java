package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import static frc.robot.Constants.*;

public class ShooterPIDCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsystem m_subsystem;
    private Timer m_timer = new Timer();
    private boolean stable = false;
    private double fireRPM = 0;
  
    /**
     * Creates a new ShooterPIDCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShooterPIDCommand(ShooterSubsystem subsystem) {
      Shuffleboard.getTab("SmartDashboard").addNumber("Firing RPM", () -> fireRPM);
      m_subsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      m_subsystem.setShooterPID(TARGET_VELOCITY);

      // Check shooter velocity within a small range to avoid missing shooting opportunity.
      if (m_subsystem.getShooterVelocity() > 4180 && m_subsystem.getShooterVelocity() < 4220) {
        if (stable) {
          m_timer.start();
          m_subsystem.setIntakeMotors(SHOOTER_POWER);
        }
        stable = true;
      }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      fireRPM = m_subsystem.getShooterVelocity();
      
      stable = false;
      m_timer.stop();
      m_timer.reset();

      m_subsystem.setIntakeMotors(0);
      m_subsystem.resetShooterPID();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_timer.hasElapsed(0.5);
    }
}