package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import java.util.function.DoubleSupplier;

public class DriveCartesianCommand extends CommandBase {
    private final DriveSubsystem m_subsystem;
    private final DoubleSupplier m_x;
    private final DoubleSupplier m_y;
    private final DoubleSupplier m_rotate;
    private final double m_speedScale;
    
    public DriveCartesianCommand(DoubleSupplier x, DoubleSupplier y, DoubleSupplier rotate, DriveSubsystem subsystem, double speedScale) {
      m_x = x;
      m_y = y;
      m_rotate = rotate;  
      m_subsystem = subsystem;
      m_speedScale = speedScale;

        addRequirements(subsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.setDriveCartesian(m_y.getAsDouble() * m_speedScale, m_x.getAsDouble() * m_speedScale, m_rotate.getAsDouble() * m_speedScale);
    if (m_x.getAsDouble() == 0 && m_y.getAsDouble() == 0 && m_rotate.getAsDouble() == 0) {
      m_subsystem.getFREncoder().setPosition(0);
      m_subsystem.getFLEncoder().setPosition(0);
      m_subsystem.getBREncoder().setPosition(0);
      m_subsystem.getBLEncoder().setPosition(0);
    } 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
