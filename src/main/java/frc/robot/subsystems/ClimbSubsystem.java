package frc.robot.subsystems;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Ports.*;
import static frc.robot.Constants.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ClimbSubsystem extends SubsystemBase {

    private final CANSparkMax m_left, m_right;

    /** Creates a new ExampleSubsystem. */
    public ClimbSubsystem() {
        m_left = new CANSparkMax(CLIMB_LEFT, MotorType.kBrushless);
        m_right = new CANSparkMax(CLIMB_RIGHT, MotorType.kBrushless);

        Shuffleboard.getTab("SmartDashboard").addNumber("Left Climb", () -> m_left.getEncoder().getPosition());
        Shuffleboard.getTab("SmartDashboard").addNumber("Right Climb", () -> m_right.getEncoder().getPosition());
    }

    public void setMotors(double left, double right) {
        if (left < 0 || right > 0) {
          double errorLeft = MAX_CLIMB_POS - getLeftEncoder().getPosition();
          double errorRight = MAX_CLIMB_POS - getRightEncoder().getPosition();

          left = errorLeft * 0.15;
          right = errorRight * 0.15;

          left = -Math.min(Math.max(left, 0), 0.75);
          right = Math.min(Math.max(right, 0), 0.75);
        }

        m_left.set(-left);
        m_right.set(right);
    }

    public RelativeEncoder getLeftEncoder() {
      return m_left.getEncoder();
    }

    public RelativeEncoder getRightEncoder() {
      return m_right.getEncoder();
    }
  
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    }
}
