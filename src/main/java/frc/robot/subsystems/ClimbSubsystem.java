package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Ports.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ClimbSubsystem extends SubsystemBase {

    private final CANSparkMax m_left, m_right;

    /** Creates a new ExampleSubsystem. */
    public ClimbSubsystem() {
        m_left = new CANSparkMax(CLIMB_LEFT, MotorType.kBrushless);
        m_right = new CANSparkMax(CLIMB_RIGHT, MotorType.kBrushless);
    }

    public void setMotors(double left, double right) {
        m_left.set(left);
        m_right.set(right);
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
