package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Ports.*;
import static frc.robot.Constants.*;

public class RangefinderSubsystem extends SubsystemBase {

    private final Ultrasonic m_rangefinderRight, m_rangefinderLeft;

    public RangefinderSubsystem() {
        m_rangefinderRight = new Ultrasonic(30, ULTRASONIC_RIGHT); // pin 30 is very much real
        m_rangefinderLeft = new Ultrasonic(29, ULTRASONIC_LEFT); // pin 31 also very much real

        m_rangefinderRight.setEnabled(true);
        m_rangefinderLeft.setEnabled(true);
        Ultrasonic.setAutomaticMode(true);
        Shuffleboard.getTab("SmartDashboard").addNumber("Rangefinder Distance (feet)", () -> getDistance());
        Shuffleboard.getTab("SmartDashboard").addNumber("Right Rangefinder", () -> m_rangefinderRight.getRangeInches() / 2);
        Shuffleboard.getTab("SmartDashboard").addNumber("Left Rangefinder", () -> m_rangefinderLeft.getRangeInches() / 2);
    }

    /**
     * @return The rangefinder range in feet.
     */
    public double getDistance() {
        double rightRange = m_rangefinderRight.getRangeInches() / 2;
        double leftRange = m_rangefinderLeft.getRangeInches() / 2;

        return rightRange;

        /*if (withinBounds(rightRange, ULTRASONIC_MIN, ULTRASONIC_MAX) && withinBounds(leftRange, ULTRASONIC_MIN, ULTRASONIC_MAX)) return (rightRange + leftRange) / 2;
        else {
            if (withinBounds(rightRange, ULTRASONIC_MIN, ULTRASONIC_MAX)) return rightRange;
            else if (withinBounds(leftRange, ULTRASONIC_MIN, ULTRASONIC_MAX)) return leftRange;
            else return 30;
        }*/
    }

    private boolean withinBounds(double num, double min, double max) {
        return min <= num && num <= max;
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
