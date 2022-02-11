package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax shooterRight = new CANSparkMax(Ports.SHOOTER_RIGHT, MotorType.kBrushless);
    private final CANSparkMax shooterLeft = new CANSparkMax(Ports.SHOOTER_LEFT, MotorType.kBrushless);
    private final TalonSRX intakeRight = new TalonSRX(Ports.INTAKE_RIGHT);
    private final TalonSRX intakeLeft = new TalonSRX(Ports.INTAKE_LEFT);
    // CANSparkMax.getRelativeEncoder(int)

    public ShooterSubsystem() {
        shooterLeft.setInverted(true);
        Shuffleboard.getTab("SmartDashboard").addNumber("Shooter Motors RPM", () -> getShooterVelocity());
    }

    public void setShooterMotors(double speed) {
        shooterRight.set(speed);
        shooterLeft.set(speed);
    }

    private final double maxRPM = 5676;
    private final double cycleTime = 0.02;

    private final double shooter_kP = 0.07;
    private final double nominalPWM = 0.7;

    private final double shooter_kI = 0.07;
    private double shooter_tI = 0;

    private final double shooter_kD = 0.07;
    private double prevError = 0;
    /**
     * Uses a PID loop to get both shooter motors to a specific, consistent RPM
     * @param targetSpeed the target speed for the motors (in RPM)
     */
    public void setShooterPID(double targetSpeed) {
        double error = targetSpeed - getShooterVelocity();

        double proportional = nominalPWM + (error * shooter_kP) / maxRPM; // divide error in rpm by max rpm of neo motor to get percentage
        proportional = Math.min(Math.max(proportional, 0), 1);

        shooter_tI += (error * shooter_kI * cycleTime) / maxRPM; // cycle time of robot (0.02 seconds = 1 tick)

        double derivative = shooter_kD * (error - prevError) / cycleTime;
        prevError = error;

        setShooterMotors(proportional + shooter_tI + derivative);
    }

    public void resetShooterPID() {
        shooter_tI = 0;
        setShooterMotors(0);
    }

    public void setIntakeMotors(double speed) {
        intakeRight.set(ControlMode.PercentOutput, speed);
        intakeLeft.set(ControlMode.PercentOutput, speed);
    }

    public double getShooterVelocity() {
        return (shooterRight.getEncoder().getVelocity() + shooterLeft.getEncoder().getVelocity()) / 2;
    }

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {}
}
