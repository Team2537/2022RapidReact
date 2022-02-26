package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.WinchSubsystem;

public class AngleShooterCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final WinchSubsystem m_subsystem;
    private final XboxController m_controller;

    private final double m_angle;
    private final int direction;
  
    /**
     * Creates a new AngleShooterCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public AngleShooterCommand(WinchSubsystem subsystem, double angle, XboxController controller) {
      m_subsystem = subsystem;
      m_angle = angle;
      m_controller = controller;

      direction = m_subsystem.getShooterAngle() < m_angle ? 1 : -1;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      System.out.println("command started");
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      m_controller.setRumble(RumbleType.kLeftRumble, 0.25);
      m_controller.setRumble(RumbleType.kRightRumble, 0.25);
      m_subsystem.setWinchAngle(m_angle);
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      m_subsystem.setWinch(0);
      m_controller.setRumble(RumbleType.kLeftRumble, 0);
      m_controller.setRumble(RumbleType.kRightRumble, 0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      if (direction == 1) {
        if (m_subsystem.getShooterAngle() < m_angle + 0.25 && m_subsystem.getShooterAngle() >= m_angle) return true;
      } else {
        if (m_subsystem.getShooterAngle() > m_angle - 0.2 && m_subsystem.getShooterAngle() <= m_angle) return true;
      }

      return false;
    }
}
