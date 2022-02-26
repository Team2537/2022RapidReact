package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Ports.*;

public class DriveSubsystem extends SubsystemBase {
    
    private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
    private final AHRS navX = new AHRS();
    private final MecanumDrive m_mecanum;

    public DriveSubsystem() {
        frontLeft = new CANSparkMax(FRONT_LEFT, MotorType.kBrushless); 
        frontRight = new CANSparkMax(FRONT_RIGHT, MotorType.kBrushless);
        backLeft = new CANSparkMax(BACK_LEFT, MotorType.kBrushless);
        backRight = new CANSparkMax(BACK_RIGHT, MotorType.kBrushless);

        m_mecanum = new MecanumDrive(frontLeft, backLeft, frontRight, backRight);
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