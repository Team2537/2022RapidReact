package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import static frc.robot.Ports.*;
import java.text.DecimalFormat;

public class WindowMotorSubsystem extends SubsystemBase {

    private double prevAngle = DEFAULT_ANGLE;
    private double angle = DEFAULT_ANGLE;

    private final CANSparkMax m_windowLeft = new CANSparkMax(WINDOW_LEFT, MotorType.kBrushed);
    private final CANSparkMax m_windowRight = new CANSparkMax(WINDOW_RIGHT, MotorType.kBrushed);

    private final DutyCycleEncoder shooterAngleEncoder = new DutyCycleEncoder(ANGLE_ENCODER);

     /** Creates a new WindowMotorSubsystem. */
    public WindowMotorSubsystem() {
        m_windowRight.setInverted(true);
        Shuffleboard.getTab("Smartdashboard").addNumber("Shooter Angle", () -> getShooterAngle());
    }

    public void setMotors(double percentOut) {
        m_windowLeft.set(percentOut);
        m_windowRight.set(percentOut);
    }

    private final double kP = 0.15;
    private final double kI = 0.005;
    private final double kD = 0.0005;

    private double tI = 0;
    private double prevError = 0;

    private final double cycleTime = 0.02;

    private void setPoint(double targetAngle) {
        double error = targetAngle - getShooterAngle();
        
        double proportional = error * kP;

        tI += error * kI * cycleTime;

        double derivative = kD * (error - prevError) / cycleTime;
        prevError = error;

        double pid = Math.min(Math.max((proportional + tI - derivative), -0.5), 0.75);

        setMotors(pid);
    }

    public void setAngle(double targetAngle) {
        angle = targetAngle;
    }

    public void stopMotors() {
        setMotors(0);
    }

    public double getShooterAngle() {
        return (shooterAngleEncoder.get() * 360 + 4.7) / MICAH_CONSTANT; // Multiply by 360 to convert revolutions to degrees.
    }

    public double getMaxAngle(double distance, double height, double initVelocity) {
        distance = Double.parseDouble(new DecimalFormat("#.##").format(distance));
        for (int i = 90000; i >= 0; i--) {
            double y =
                Math.tan(Math.toRadians(i / 1000f)) * distance - 
                (GRAVITY * distance * distance / (2f * initVelocity * initVelocity * 
                Math.cos(Math.toRadians(i / 1000f)) * Math.cos(Math.toRadians(i / 1000f))));
            if (Math.abs(height - y) < 0.001 && (i / 1000f) < 90) return (i / 1000f);
        }
        return 15;
    }

    public double getMinAngle(double distance, double height, double initVelocity) {
        distance = Double.parseDouble(new DecimalFormat("#.##").format(distance));
        for (int i = 0; i <= 90000; i++) {
            double y =
                Math.tan(Math.toRadians(i / 1000f)) * distance - 
                (GRAVITY * distance * distance / (2f * initVelocity * initVelocity * 
                Math.cos(Math.toRadians(i / 1000f)) * Math.cos(Math.toRadians(i / 1000f))));
            if (Math.abs(height - y) < 0.001 && (i / 1000f) < 90) return (i / 1000f);
        }
        return 15;
    }

    @Override
    public void periodic() {
        if (prevAngle != angle) tI = 0;

        setPoint(angle);

        prevAngle = angle;
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    } 
}
