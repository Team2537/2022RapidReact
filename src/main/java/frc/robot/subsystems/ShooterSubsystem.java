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
    }

    public void setShooterMotors(double speed) {
        shooterRight.set(speed);
        shooterLeft.set(speed);
    }

    private final double shooter_kP = 0.07;
    private final double nominalPWM = 0.7;
    /**
     * Uses a PID loop to get both shooter motors to a specific, consistent RPM
     * @param targetSpeed the target speed for the motors (in RPM)
     */
    public void setShooterMotorsPID(double targetSpeed) {
        double error = targetSpeed - getShooterVelocity();
        double percentOutput = (error * shooter_kP) / 5676; // divide error in rpm by max rpm of neo motor to get percentage
        percentOutput = Math.min(Math.max(percentOutput, 0), 1);

        shooterRight.set(percentOutput);
        shooterLeft.set(percentOutput);
    }

    public void setIntakeMotors(double speed) {
        intakeRight.set(ControlMode.PercentOutput, speed);
        intakeLeft.set(ControlMode.PercentOutput, speed);
    }

    public double getShooterVelocity() {
        return (shooterRight.getEncoder().getVelocity() + shooterLeft.getEncoder().getVelocity()) / 2;
    }

    @Override
    public void periodic() {
        Shuffleboard.getTab("Shooter Velocity");
    }

    @Override
    public void simulationPeriodic() {}
}
