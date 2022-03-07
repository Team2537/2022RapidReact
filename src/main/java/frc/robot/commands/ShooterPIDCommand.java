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
    private boolean twoLoaded;

    private static double fireRPM = 0;

    static {
      Shuffleboard.getTab("SmartDashboard").addNumber("Firing RPM", () -> fireRPM);
    }
  
    /**
     * Creates a new ShooterPIDCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShooterPIDCommand(ShooterSubsystem subsystem) {
      m_subsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      twoLoaded = m_subsystem.getBackSensorActive() && m_subsystem.getFrontSensorActive();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      m_subsystem.setShooterPID(TARGET_VELOCITY);
      // Check shooter velocity within a small range to avoid missing shooting opportunity.
      if (Math.abs(m_subsystem.getShooterVelocity() - TARGET_VELOCITY) <= 25) {
        m_subsystem.setIntakeMotors(INTAKE_POWER);
        m_timer.start();
      }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      fireRPM = m_subsystem.getShooterVelocity();

      m_timer.stop();
      m_timer.reset();

      m_subsystem.setIntakeMotors(0);
      m_subsystem.resetShooterPID();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      if (twoLoaded) return !m_subsystem.getBackSensorActive();
      else return m_timer.hasElapsed(1.5);
    }
}