package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;

public class DriveSubsystem extends SubsystemBase {
    
    private final CANSparkMax frontLeft = new CANSparkMax(Ports.FRONT_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax frontRight = new CANSparkMax(Ports.FRONT_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax backLeft = new CANSparkMax(Ports.BACK_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax backRight = new CANSparkMax(Ports.BACK_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
    private MecanumDrive m_mecanum = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);

    public DriveSubsystem() {
        frontLeft.setInverted(true);
        backLeft.setInverted(true);
    }

    public void setDriveCartesian(double ySpeed, double xSpeed, double zRotation) {
        m_mecanum.driveCartesian(ySpeed, xSpeed, zRotation);
    }

    public void stop() {
        setDriveCartesian(0,0,0);
    }

    public void setFrontLeft(double speed) {
        frontLeft.set(speed);
    }

    public void setFrontRight(double speed) {
        frontRight.set(speed);
    }

    public void setBackLeft(double speed) {
        backLeft.set(speed);
    }

    public void setBackRight(double speed) {
        backRight.set(speed);
    }

    public void setAll(double speed) {
        setFrontLeft(speed);
        setFrontRight(speed);
        setBackLeft(speed);
        setBackRight(speed);
    }

    @Override
    public void periodic() {}
}
