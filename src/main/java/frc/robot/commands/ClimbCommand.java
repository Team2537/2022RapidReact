package frc.robot.commands;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimbSubsystem;

public class ClimbCommand extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})

    private final File encoderPositions;
    private FileWriter writer;

    private final ClimbSubsystem m_subsystem;
    private final DoubleSupplier m_left, m_right;

    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public ClimbCommand(ClimbSubsystem subsystem, DoubleSupplier left, DoubleSupplier right) {
      m_subsystem = subsystem;
      m_left = left;
      m_right = right;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);

      encoderPositions = new File(Filesystem.getDeployDirectory(), "EncoderPositions.txt");
      try {
        if (encoderPositions.createNewFile()) {
          System.out.println("Created encoder positions file.");
        } else {
          Scanner scanner = new Scanner(encoderPositions);
          m_subsystem.getLeftEncoder().setPosition(scanner.nextDouble());
          m_subsystem.getRightEncoder().setPosition(scanner.nextDouble());
          scanner.close();
        }

        writer = new FileWriter(encoderPositions);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_subsystem.setMotors(m_left.getAsDouble(), m_right.getAsDouble());
        
        try {
          writer.write(String.format("%f %f", 
            m_subsystem.getLeftEncoder().getPosition(), m_subsystem.getRightEncoder().getPosition()));
        } catch (Exception e) {
          e.printStackTrace();
        }
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    } 
}
