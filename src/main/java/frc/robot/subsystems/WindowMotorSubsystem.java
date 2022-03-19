package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;
import java.text.DecimalFormat;

public class WindowMotorSubsystem extends SubsystemBase {
    private final CANSparkMax m_windowLeft = new CANSparkMax(12, MotorType.kBrushed);
    private final CANSparkMax m_windowRight = new CANSparkMax(8, MotorType.kBrushed);

    private final DutyCycleEncoder shooterAngleEncoder = new DutyCycleEncoder(1);

     /** Creates a new WindowMotorSubsystem. */
    public WindowMotorSubsystem() {
        m_windowRight.setInverted(true);
        Shuffleboard.getTab("Smartdashboard").addNumber("Shooter Angle", () -> getShooterAngle());
    }

    public void setMotors(double percentOut) {
        m_windowLeft.set(percentOut);
        m_windowRight.set(percentOut);
    }

    private final double kP = 0.025;

    private final double kI = 0.2;
    private double tI = 0;

    private final double kD = 0.00085;
    private double prevError = 0;
    private final double cycleTime = 0.021;

    public void setAngle(double targetAngle) {
        /*double error = targetAngle - getShooterAngle();
        
        double proportional = error * kP;

        tI += error * kI * cycleTime;

        double derivative = kD * (error - prevError) / cycleTime;
        prevError = error;

        double pid = Math.min(Math.max((proportional + tI - derivative), -0.6), 0.6);
        setMotors(pid);*/

        double error = targetAngle - getShooterAngle();

        double power = Math.min(Math.max(error * 0.25, -0.6), 1);
        setMotors(power);
    }

    public void stopMotors() {
        tI = 0;
        setMotors(0.1);
    }

    public double getShooterAngle() {
        return (shooterAngleEncoder.get() * 360 + 4.7) / 0.9444444; // Multiply by 360 to convert revolutions to degrees.
    }

    public double getAngle(double distance, double height, double initVelocity) {
        distance = Double.parseDouble(new DecimalFormat("#.##").format(distance));
        for (int i = 90000; i >= 0; i--) {
            double y =
                Math.tan(Math.toRadians(i / 1000f)) * distance - 
                (GRAVITY * distance * distance / (2f * initVelocity * initVelocity * 
                Math.cos(Math.toRadians(i / 1000f)) * Math.cos(Math.toRadians(i / 1000f))));
            if (Math.abs(height - y) < 0.001 && 90 - (i/1000f) >= 13) return (i / 1000f);
        }
        return 15;
        /*
        double time, testHeight, angle;
        for (int i=90000; i>=0; i--){
             angle = i/1000;
             time= distance/(initVelocity*Math.cos(Math.toRadians(angle)));
             testHeight= initVelocity*time + 0.5*GRAVITY*Math.pow(time, 2);
             if (Math.abs(height-testHeight)<=0.001 && (90 - angle) > 15) {
                 System.out.println(90 - angle);
                 return 90 - angle;
             }
        }
        System.out.println("Failed to find angle");
        return 15;*/
    }

    @Override
    public void periodic() {
    }
  
    @Override
    public void simulationPeriodic() {
      // This method will be called once per scheduler run during simulation
    } 
}
