package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwapCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private boolean swapped = false;
    Command m_commandOne, m_commandTwo;
  
    /**
     * Creates a new SwapControlsCommand.
     */
    public SwapCommand(Command commandOne, Command commandTwo) {
        m_commandOne = commandOne;
        m_commandTwo = commandTwo;
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (!swapped) {
            m_commandTwo.cancel();
            m_commandOne.schedule();
        } else {
            m_commandOne.cancel();
            m_commandTwo.schedule();
        }

        swapped = !swapped;
        return true;
    }
}
