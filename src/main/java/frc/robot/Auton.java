package frc.robot;

import java.util.LinkedList;
import java.util.Queue;

import frc.subsystems.DrivetrainController;

/**
 * Encapsulates autonomous routine
 */
public class Auton {
    final AutonSequence sequence;
    final Position position;
    final DrivetrainController controller;

    public enum Stage {
        ONE, TWO, THREE, FOUR
    }

    private Stage stage = Stage.ONE;
    private boolean doneWithStage = false;

    private enum MotionType {
        ROTATION,
        DRIVE,
        LIFT_AND_SHOOT,
        CONSUME_BALL
    }

    private class Motion {
        private double amount;
        private MotionType type;

        public Motion(double amount, MotionType type) { 
            this.amount = amount; 
            this.type = type; 
        }

        public double getAmount() { return amount; }
        public MotionType getType() { return type; }
    }

    // store motion queue
    private Queue<Motion> motions = new LinkedList<>();

    public Auton(Tarmac startingTarmac, DrivetrainController controller) {
        sequence = AutonSequences.getSequence(startingTarmac);
        position = sequence.getStart();
        this.controller = controller;
    }

    /**
     * Calculate angle/distance required to turn and drive to reach an arbitrary location,
     * and add to motion queue
     * @param to
     */
    private void navigate(Location to) {
        double dX = to.getX() - position.getLocation().getX();
        double dY = to.getY() - position.getLocation().getY();

        double targetAngle = Math.atan2(dY, dX);

        double dAngle = position.getAngle() - targetAngle;
        double distance = Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));

        motions.add(new Motion(dAngle, MotionType.ROTATION));
        motions.add(new Motion(distance, MotionType.DRIVE));
    }

    public void update(boolean userReady) {
        if (controller.available() && !motions.isEmpty()) {
            Motion motion = motions.remove();

            switch (motion.getType()) {
                case ROTATION:
                    controller.rotateRadians(motion.getAmount());                
                    break;
                case DRIVE:
                    controller.driveInches(motion.getAmount());
                case LIFT_AND_SHOOT:
                case CONSUME_BALL:
                    // TODO
            }
        } else if (controller.available() && doneWithStage && userReady) {
            switch (stage) {
                case ONE: stageTwo(); break;
                case TWO: stageThree(); break;
                case THREE: stageFour(); break;
                case FOUR: /* DONE! */ break;
            }
        }

        controller.update(0, 0);
    }

    /**
     * Start auton routine
     */
    public void start() {
        stageOne();
    }

    /**
     * Stage 1: Shoot pre-loaded ball
     */
    public void stageOne() {
        // TODO: Shoot pre-loaded ball into low goal
        System.out.println("Starting stage one...");

        motions.add(new Motion(0, MotionType.LIFT_AND_SHOOT));
    }

    /**
     * Stage 2: Drive to nearest ball
     */
    public void stageTwo() {
        stage = Stage.TWO;
        doneWithStage = false;

        System.out.println("Starting stage two...");

        navigate(sequence.getBall());
    }

    /**
     * Stage 3: Intake reached ball
     */
    public void stageThree() {
        stage = Stage.THREE;
        doneWithStage = false;

        System.out.print("Starting stage three...");

        motions.add(new Motion(0, MotionType.CONSUME_BALL));
    }

    /**
     * Stage 4: Drive to terminal
     */
    public void stageFour() {
        stage = Stage.FOUR;
        doneWithStage = false;

        System.out.print("Starting stage four...");

        navigate(sequence.getTerminal());
    }
}
