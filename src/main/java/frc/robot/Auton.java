package frc.robot;

import java.util.EnumMap;

import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
import frc.subsystems.IntakeMode;
import frc.utils.SleepUtil;

/**
 * Encapsulates autonomous routine
 */
public class Auton {
    final AutonSequence sequence;
    final Position position;

    public Auton(Tarmac startingTarmac) {
        sequence = AutonSequences.getSequence(startingTarmac);
        position = sequence.getStart();
    }

    public Auton(Position startingPosition) {
        position = startingPosition;
        sequence = null;
    }

    public void navigate(Drivetrain drivetrain, Location to) {
        double dX = to.getX() - position.getLocation().getX();
        double dY = to.getY() - position.getLocation().getY();

        double targetAngle = Math.atan2(dY, dX);

        double dAngle = position.getAngle() - targetAngle;
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        drivetrain.rotate(dAngle);
        drivetrain.driveInches(distance);

        position.setAngle(targetAngle);
        position.setLocation(to);
    }

    /**
     * Stage 1: Shoot pre-loaded ball
     */
    public void stageOne() {
        // TODO: Shoot pre-loaded ball into low goal
        System.out.println("Starting stage one...");
    }

    /**
     * Stage 2: Drive to nearest ball
     */
    public void stageTwo(Drivetrain drivetrain) {
        System.out.println("Starting stage two...");
    }

    /**
     * Stage 3: Intake reached ball
     */
    public void stageThree(Intake intake) {
        System.out.print("Starting stage three...");
    }

    /**
     * Stage 4: Drive to terminal
     */
    public void stageFour(Drivetrain drivetrain) {
        System.out.print("Starting stage four...");
    }
}
