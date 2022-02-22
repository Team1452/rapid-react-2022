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

    private void navigate(Drivetrain drivetrain, Location to) {
        double dX = to.getX() - position.getLocation().getX();
        double dY = to.getY() - position.getLocation().getY();

        double targetAngle = Math.tan(dY / dX);

        double dAngle = position.getAngle() - targetAngle;
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        drivetrain.rotate(dAngle);
        drivetrain.drive(distance);

        position.setAngle(targetAngle);
        position.setLocation(to);
    }

    /**
     * Stage 1: Shoot pre-loaded ball
     */
    public void stageOne() {
        // TODO: Shoot pre-loaded ball into low goal
    }

    /**
     * Stage 2: Drive to nearest ball
     */
    public void stageTwo(Drivetrain drivetrain) {
        /* TODO:
            1. Rotate left to have intake face ball
            2. Move forward to ball
        */

        // TODO: rotate/drive using gyro and encoders
        drivetrain.rotate(100);
        SleepUtil.sleep(30);
        drivetrain.stop();

        drivetrain.drive(20, 0);
        SleepUtil.sleep(30);
        drivetrain.stop();
    }

    /**
     * Stage 3: Intake reached ball
     */
    public void stageThree(Intake intake) {
        intake.setMode(IntakeMode.INWARD);
        intake.set(30);

        SleepUtil.sleep(20);

        intake.stop();
    }

    /**
     * Stage 4: Drive to terminal
     */
    public void stageFour(Drivetrain drivetrain) {
        // TODO: input actual rotation/drive amounts
        drivetrain.rotate(100);
        SleepUtil.sleep(30);

        drivetrain.drive(30, 0);
        SleepUtil.sleep(30);
    }
}
