package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;

/** An example command that uses an example subsystem. */
public class DriveSetDistanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_subsystem;
  private final double kp = 0.05;
  private final double ki = 0;
  private final double kd = 0;
  private PIDController m_controller = new PIDController(kp, ki, kd);
  private double m_distance, PID;
  private double initBL, initBR, initFL, initFR; //initial encoder position (BL = BackLeft, BR=BackRight, FL=FrontLeft, FR=FrontRight)
  private double deltaBL, deltaBR, deltaFL, deltaFR; //change in encoder position


  /**
   * Creates a new DriveSetDistanceCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveSetDistanceCommand(DriveSubsystem subsystem, double distance) {
    m_distance = distance>0 ? distance+4 : distance-4; //desired distance is always off by 4; this fixes that
    m_subsystem = subsystem;
    m_controller.setSetpoint(m_distance);
    
    
    
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    initBL = m_subsystem.getBLEncoder().getPosition();
    initBR =  m_subsystem.getBREncoder().getPosition();
    initFL =  m_subsystem.getFLEncoder().getPosition();
    initFR =  m_subsystem.getFREncoder().getPosition();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      deltaBL = m_subsystem.getBLEncoder().getPosition() - initBL;
      deltaBR = m_subsystem.getBREncoder().getPosition() - initBR;
      deltaFL = m_subsystem.getFLEncoder().getPosition() - initFL;
      deltaFR = m_subsystem.getFREncoder().getPosition() - initFR;

      double avgDeltaEncoderPosition = (deltaBL + deltaBR + deltaFL + deltaFR)/4;
      PID = m_controller.calculate(m_subsystem.encoderToInches(avgDeltaEncoderPosition));
      PID = Math.max(Math.min(0.1,PID),-0.1); //Scales PID btwn -1 to 1 if not already
      //Shuffleboard.getTab("SmartDashboard").addNumber("PID Value:", PID);
    m_subsystem.setAll(PID);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_controller.atSetpoint();
  }
}
