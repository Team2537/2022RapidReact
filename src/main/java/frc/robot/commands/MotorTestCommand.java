package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class MotorTestCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    double time = 25;
    private final DriveSubsystem m_subsystem;
    private Timer m_timer = new Timer();
  
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public MotorTestCommand(DriveSubsystem subsystem) {
      m_subsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_timer.start();
        m_subsystem.setFrontLeft(0.5);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_timer.hasElapsed(time * 3)) {
            m_subsystem.setFrontLeft(0.5);
        } else if (m_timer.hasElapsed(time * 2)) {
            m_subsystem.setBackLeft(0.5);
        } else if (m_timer.hasElapsed(time)) {
            m_subsystem.setFrontRight(0.5);
        } else {
            m_subsystem.setBackRight(0.5);
        }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_timer.reset();
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_timer.hasElapsed(time * 4);
    }
}
