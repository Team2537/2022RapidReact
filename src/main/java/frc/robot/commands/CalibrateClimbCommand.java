package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class CalibrateClimbCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final ClimbSubsystem m_subsystem;
    private final Timer m_timer = new Timer();
  
    /**
     * Creates a new CalibrateClimbCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public CalibrateClimbCommand(ClimbSubsystem subsystem) {
      m_subsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_timer.start();
        m_subsystem.setMotors(-0.5, -0.5);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_timer.hasElapsed(0.1)) {
            double left = -0.5, right = -0.5;
            if (m_subsystem.getRightEncoder().getVelocity() > -2) right = 0;
            if (m_subsystem.getLeftEncoder().getVelocity() > -2) left = 0;

            m_subsystem.setMotors(left, right);
        }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_subsystem.getLeftEncoder().setPosition(0);
        m_subsystem.getRightEncoder().setPosition(0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_timer.hasElapsed(0.1) && m_subsystem.getRightEncoder().getVelocity() > -2 && m_subsystem.getLeftEncoder().getVelocity() > -2;
    }
}
