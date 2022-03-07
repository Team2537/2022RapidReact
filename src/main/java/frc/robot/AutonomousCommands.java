package frc.robot;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AngleShooterCommand;
import frc.robot.commands.DriveSetDistanceCommand;
import frc.robot.commands.ShooterPIDCommand;

public class AutonomousCommands extends SequentialCommandGroup {

    private final ShooterPIDCommand m_shooterPIDCommand;

    public AutonomousCommands(DriveSetDistanceCommand distanceCommand, AngleShooterCommand angleCommand, ShooterPIDCommand shooterCommand) {
        m_shooterPIDCommand = shooterCommand;
        addCommands(distanceCommand.alongWith(angleCommand));
    }

    public void end(boolean interrupted) {
        m_shooterPIDCommand.schedule();
    }
}
