package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Ports.*;

public class DriveSubsystem extends SubsystemBase {
    
    private final CANSparkMax frontLeft, frontRight, backLeft, backRight;
    private final MecanumDrive m_mecanum;

    public DriveSubsystem() {
        frontLeft = new CANSparkMax(FRONT_LEFT, MotorType.kBrushless); 
        frontRight = new CANSparkMax(FRONT_RIGHT, MotorType.kBrushless);
        backLeft = new CANSparkMax(BACK_LEFT, MotorType.kBrushless);
        backRight = new CANSparkMax(BACK_RIGHT, MotorType.kBrushless);

        backLeft.setInverted(true);
        frontLeft.setInverted(true);

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

    public RelativeEncoder getBLEncoder() { //BL = backLeft
        return backLeft.getEncoder();
    }

    public RelativeEncoder getBREncoder() { //BR = backRight
        return backRight.getEncoder();
    }

    public RelativeEncoder getFLEncoder() { //FL = frontLeft
        return frontLeft.getEncoder();
    }

    public RelativeEncoder getFREncoder() { // FR = frontRight
        return frontRight.getEncoder();
    }


    public double encoderToInches(double encoderPosition) {
        return encoderPosition*(24/23)*Math.PI;
    }



    @Override
    public void periodic() {}
}
