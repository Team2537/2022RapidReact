package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;

/** An example command that uses an example subsystem. */
public class DriveSetDistanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_subsystem;
  //private PIDController m_controller = new PIDController(kp, ki, kd);

  /**
   * Creates a new DriveSetDistanceCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveSetDistanceCommand(DriveSubsystem subsystem, double distance) {
    m_subsystem = subsystem;
    //m_controller.setSetpoint(distance);

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      //m_controller.calculate();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //if(m_controller.atSetpoint()) {
       // return true;
    //}
  return true;
    }
}
