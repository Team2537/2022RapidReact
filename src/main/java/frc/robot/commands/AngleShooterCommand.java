package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WindowMotorSubsystem;

public class AngleShooterCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final WindowMotorSubsystem m_subsystem;
    private static XboxController m_controller;

    private final Timer m_timer = new Timer();

    private final double m_angle;
  
    /**
     * Creates a new AngleShooterCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AngleShooterCommand(WindowMotorSubsystem subsystem, double angle) {
      m_subsystem = subsystem;
      m_angle = angle;

      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      m_subsystem.setAngle(m_angle);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

      if (Math.abs(m_angle - m_subsystem.getShooterAngle()) < 0.5) {
        m_timer.start();
      } else {
        m_timer.stop();
        m_timer.reset();
      }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_timer.hasElapsed(0.25);
    }

    public static void setController(XboxController controller) {
      m_controller = controller;
    }
}
