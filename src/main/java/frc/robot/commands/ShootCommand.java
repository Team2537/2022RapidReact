package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;
import static frc.robot.Constants.*;

public class ShootCommand extends CommandBase {

    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ShooterSubsystem m_subsystem;
    private Timer m_timer;
  
    /**
     * Creates a new ShootCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ShootCommand(ShooterSubsystem subsystem) {
      m_subsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_timer = new Timer();
        m_subsystem.setShooterMotors(SHOOTER_POWER);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_subsystem.getShooterVelocity() > TARGET_VELOCITY) {
            m_timer.start();
            m_subsystem.setIntakeMotors(INTAKE_POWER);
        }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_subsystem.setIntakeMotors(0);
        m_subsystem.setShooterMotors(0);
        m_timer.stop();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_timer.hasElapsed(END_OFFSET);
    }
}
