package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class SparkMaxController implements MotorController {
    private final CANSparkMax m_sparkMax;

    public SparkMaxController(int id) {
        m_sparkMax = new CANSparkMax(id, MotorType.kBrushless);
    }

    @Override
    public void set(double speed) {
        m_sparkMax.set(speed);
    }

    @Override
    public double get() {
        return m_sparkMax.get();
    }

    @Override
    public void setInverted(boolean inverted) {
        m_sparkMax.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return m_sparkMax.getInverted();
    }

    @Override
    public void stopMotor() {
        m_sparkMax.stopMotor();
    }

    @Override
    public void disable() {
        m_sparkMax.disable();
    }
}
