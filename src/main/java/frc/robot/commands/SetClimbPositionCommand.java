package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class SetClimbPositionCommand extends CommandBase {

    private final ClimbSubsystem m_subsystem;
    private final double m_targetPosition;

    public SetClimbPositionCommand(ClimbSubsystem subsystem, double targetPosititon) {
        m_subsystem = subsystem;
        m_targetPosition = targetPosititon;

        addRequirements(subsystem);
    }

    private boolean finished = false;
    
    @Override
    public void initialize() {
        finished = false;
    }

    @Override
    public void execute() {
        double errorLeft = m_targetPosition - m_subsystem.getLeftEncoder().getPosition();
        double errorRight = m_targetPosition - m_subsystem.getRightEncoder().getPosition();

        if (Math.abs(errorLeft) < 3 && Math.abs(errorRight) < 3) finished = true;

        m_subsystem.setMotors(-errorLeft * 0.05, errorRight * 0.05);
    }

    @Override
    public void end(boolean interrupted) {
        m_subsystem.setMotors(0, 0);
    }

    @Override
    public boolean isFinished() {
        return finished;
    }
}
