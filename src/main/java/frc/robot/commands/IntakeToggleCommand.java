package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class IntakeToggleCommand extends CommandBase {
    
    boolean intakeOn = false;

    private final IntakeCommand m_intakeCommand;
    private final AngleShooterCommand m_endAngle;

    public IntakeToggleCommand(IntakeCommand intakeCommand, AngleShooterCommand endAngle) {
        m_intakeCommand = intakeCommand;
        m_endAngle = endAngle;
    }

    @Override
    public void initialize() {
        if (intakeOn) {
            m_intakeCommand.cancel();
            m_endAngle.schedule();
        } else {
            m_intakeCommand.schedule();
        }

        intakeOn = !intakeOn;
    }

    public boolean isFinished() {
        return true;
    }
}
