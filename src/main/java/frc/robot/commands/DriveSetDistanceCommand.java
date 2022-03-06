package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/** An example command that uses an example subsystem. */
public class DriveSetDistanceCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveSubsystem m_subsystem;
  private final double kp = 0.05;
  private final double ki = 0;
  private final double kd = 0;
  private PIDController m_controller = new PIDController(kp, ki, kd);
  private double initBL; //initial backLeft encoder position
  private double initBR; 
  private double initFL;
  private double initFR;
  private double deltaBL; //change in backLeft encoder position
  private double deltaBR;
  private double deltaFL;
  private double deltaFR;
  private double PID;


  /**
   * Creates a new DriveSetDistanceCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveSetDistanceCommand(DriveSubsystem subsystem, double distance) {
    
    m_subsystem = subsystem;
    m_controller.setSetpoint(distance);
    
    
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
      System.out.println("PID Value: " + PID);
      PID = Math.max(Math.min(0.1,PID),-0.1); //Scales PID btwn -1 to 1 if not already
      System.out.println("PID Value after scaling: " + PID);
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
    if(m_controller.atSetpoint()) {
       // return true;
    }
    return false;
    }
}
