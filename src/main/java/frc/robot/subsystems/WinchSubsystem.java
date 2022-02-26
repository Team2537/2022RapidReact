package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class WinchSubsystem extends SubsystemBase {
    private final CANSparkMax m_winch = new CANSparkMax(8, MotorType.kBrushed);
    private final DutyCycleEncoder shooterAngleEncoder = new DutyCycleEncoder(1);

     /** Creates a new WinchSubsystem. */
    public WinchSubsystem() {
        Shuffleboard.getTab("Smartdashboard").addNumber("Shooter Angle", () -> getShooterAngle());
    }

    public void setWinch(double percentOut) {
        m_winch.set(percentOut);
    }

    public void setWinchAngle(double targetAngle) {
        double error = targetAngle - getShooterAngle();
        double speed = Math.abs(error) < 1 ? 0.25 : 1;

        setWinch(error < 0 ? speed : -speed);
    }

    public double getShooterAngle() {
        return shooterAngleEncoder.get() * 360; // Multiply by 360 to convert revolutions to degrees.
    }

    @Override
    public void periodic() {
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    } 
}
