package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * <a href="https://global.sharp/products/device/lineup/data/pdf/datasheet/gp2y0a21yk_e.pdf">Adafruit Infrared Distance Sensor</a>
 */
public class IRSensor {

    private final AnalogInput m_sensor;

    public IRSensor(int analogPort) {
        m_sensor = new AnalogInput(analogPort);

        Shuffleboard.getTab("SmartDashboard").addNumber(String.format("IR Sensor %d Voltage", analogPort), () -> m_sensor.getVoltage());
    }

    /**
     * @return Whether or not there is an object within 7 cm of the sensor.
     */
    public boolean getActive() {
        return m_sensor.getVoltage() < 1.25;
    }
}
