package frc.robot;

import java.util.List;

import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;
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

    /**
     * Get arctan w/ quadrant from 0 to 2pi as absolute angle
     */
    private double absoluteAtan(double y, double x) {
        double angle = Math.atan(y / x);

        if (x < 0) angle += Math.PI;
        else if (y < 0) angle += 2.0 * Math.PI;

        return angle;
    }

    public void rotateTo(Drivetrain drivetrain, double targetAngle) {
        System.out.println("  - rotateTo() : currentPos: " + position.getAngle() + ", " + "targetAngle: " + targetAngle);
        double delta = Math.abs(position.getAngle() - targetAngle);

        // if angle > 180deg, then turn other way for less distance
        if (delta > Math.PI) delta = 2.0 * Math.PI - delta;

        drivetrain.rotate(delta);
        position.setAngle(targetAngle);
    }

    public void navigate(Drivetrain drivetrain, Location to) {
        double dX = to.getX() - position.getLocation().getX();
        double dY = to.getY() - position.getLocation().getY();

        double targetAngle = absoluteAtan(dY, dX);

        // rotate absolute robot angle to face point
        System.out.println("ROTATING...");
        rotateTo(drivetrain, targetAngle);

        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        // drive to point
        System.out.println("DRIVING..."); 
        drivetrain.driveInches(distance);

        position.setAngle(targetAngle);
        position.setLocation(to);
    }

    public void navigate(Drivetrain drivetrain, List<Location> locations) {
        for (Location to : locations) {
            navigate(drivetrain, to);
            System.out.println("Starting new stage");
            SleepUtil.sleep(2000);
        }
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
