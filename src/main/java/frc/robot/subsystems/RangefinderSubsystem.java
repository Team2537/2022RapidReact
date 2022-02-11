package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class RangefinderSubsystem extends SubsystemBase {

    private final Ultrasonic m_rangefinder;

    public RangefinderSubsystem() {
        m_rangefinder = new Ultrasonic(0, 1);
        m_rangefinder.setEnabled(true);
        Ultrasonic.setAutomaticMode(true);
        Shuffleboard.getTab("SmartDashboard").addNumber("Rangefinder Distance (inches)", () -> getDistance());
    }

    public double getDistance() {
        return m_rangefinder.getRangeInches();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        System.out.println(getDistance());
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }
}
