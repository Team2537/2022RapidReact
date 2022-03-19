package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WindowMotorSubsystem;

public class WindowMotorsTestCommand extends CommandBase{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final WindowMotorSubsystem m_subsystem;

    private final DoubleSupplier m_up, m_down;
  
    /**
     * Creates a new ExampleCommand.
     *cd
     * @param subsystem The subsystem used by this command.
     */
    public WindowMotorsTestCommand(WindowMotorSubsystem subsystem, DoubleSupplier up,  DoubleSupplier down) {
      m_subsystem = subsystem;
      m_up = up;
      m_down = down;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_up.getAsDouble() != 0) m_subsystem.setMotors(m_up.getAsDouble());
        else m_subsystem.setMotors(-m_down.getAsDouble());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
