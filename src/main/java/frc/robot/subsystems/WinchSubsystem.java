package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

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
        double speed = Math.abs(error) < 2 ? 0.15 : 1;

        setWinch(error < 0 ? speed : -speed);
    }

    public double getShooterAngle() {
        return shooterAngleEncoder.get() * 360; // Multiply by 360 to convert revolutions to degrees.
    }

    public double getAngle(double distance, double height, double initVelocity){
        for (int i = 90000; i >= 0; i--) {
            double y = 
                Math.tan(Math.toRadians(i / 1000f)) * distance - 
                (GRAVITY * distance * distance / (2f * initVelocity * initVelocity * 
                Math.cos(Math.toRadians(i / 1000f)) * Math.cos(Math.toRadians(i / 1000f))));

            if (Math.abs(height - y) < 0.001 && 90 - (i/1000f) > 15) return 90 - (i / 1000f);
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
