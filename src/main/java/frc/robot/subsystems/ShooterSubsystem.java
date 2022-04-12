package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.IRSensor;
import frc.robot.Ports;
import java.lang.Math;

public class ShooterSubsystem extends SubsystemBase {

    private final CANSparkMax shooterRight = new CANSparkMax(Ports.SHOOTER_RIGHT, MotorType.kBrushless);
    private final CANSparkMax shooterLeft = new CANSparkMax(Ports.SHOOTER_LEFT, MotorType.kBrushless);
    private final TalonSRX intakeRight = new TalonSRX(Ports.INTAKE_RIGHT);
    private final TalonSRX intakeLeft = new TalonSRX(Ports.INTAKE_LEFT);

    private final IRSensor m_backSensor = new IRSensor(1);
    private final IRSensor m_frontSensor =  new IRSensor(0);

    public ShooterSubsystem() {
        shooterLeft.setInverted(true);

        ShuffleboardTab smartDashboard = Shuffleboard.getTab("SmartDashboard");
        smartDashboard.addNumber("Shooter Motors RPM", () -> getShooterVelocity());

    }

    public void setShooterMotors(double speed) {
        shooterRight.set(speed);
        shooterLeft.set(speed);
    }

    private final double maxRPM = 5676;
    private final double cycleTime = 0.02;

    private final double shooter_kP = 0.115;
    private final double nominalPWM = 0.605;

    private final double shooter_kI = 0.03;
    private double shooter_tI = 0;

    private final double shooter_kD = 0.0085;
    private double prevError = 0;
    /**
     * Uses a PID loop to get both shooter motors to a specific, consistent RPM
     * @param targetSpeed the target speed for the motors (in RPM)
     */
    public void setShooterPID(double targetSpeed) {
        double error = targetSpeed - getShooterVelocity();

        double proportional = nominalPWM + (error * shooter_kP) / maxRPM; // divide error in rpm by max rpm of neo motor to get percentage

        shooter_tI += (error * shooter_kI * cycleTime) / maxRPM; // cycle time of robot (0.02 seconds = 1 tick)

        double derivative = (shooter_kD * (error - prevError) / cycleTime) / maxRPM;
        prevError = error;
        
        double pid = Math.min(Math.max((proportional + shooter_tI - derivative), 0), 1);
        setShooterMotors(pid);
    }

    public void resetShooterPID() {
        shooter_tI = 0;
        setShooterMotors(0);
    }

    public void setIntakeMotors(double speed) {
        intakeRight.set(ControlMode.PercentOutput, speed);
        intakeLeft.set(ControlMode.PercentOutput, speed);
    }
    /**
     * @return The angular velocity of the shooter motors in RPM.
     */
    public double getShooterVelocity() {
        return (shooterRight.getEncoder().getVelocity() + shooterLeft.getEncoder().getVelocity()) / 2;
    }

    public boolean shooterMotorsClose(double target, double range) {
        double errorLeft = Math.abs(target - shooterLeft.getEncoder().getVelocity());
        double errorRight = Math.abs(target - shooterRight.getEncoder().getVelocity());

        return errorLeft <= range && errorRight <= range;
    }

    public boolean getBackSensorActive() {
        return m_backSensor.getActive();
    }

    public boolean getFrontSensorActive() {
        return m_frontSensor.getActive();
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {}
}
