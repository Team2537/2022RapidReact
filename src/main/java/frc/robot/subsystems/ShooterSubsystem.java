package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax shooterRight = new CANSparkMax(Ports.SHOOTER_RIGHT, MotorType.kBrushless);
    private final CANSparkMax shooterLeft = new CANSparkMax(Ports.SHOOTER_LEFT, MotorType.kBrushless);
    private final TalonSRX intakeRight = new TalonSRX(Ports.INTAKE_RIGHT);
    private final TalonSRX intakeLeft = new TalonSRX(Ports.INTAKE_LEFT);

    public ShooterSubsystem() {
        shooterLeft.setInverted(true);
    }

    public void setShooterMotors(double speed) {
        shooterRight.set(speed);
        shooterLeft.set(speed);
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
