package frc.robot.auton;

import java.util.Optional;

import frc.robot.Tarmac;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;

/**
 * Encapsulates autonomous routine
 */
public class Auton {
    final AutonState state;

    public Auton(Tarmac tarmac, DrivetrainController controller, Intake intake) {
        this(AutonSequences.getSequence(tarmac), controller, intake);
    }

    public Auton(AutonSequence sequence, DrivetrainController controller, Intake intake) {
        state = new AutonState(sequence.getStartingPosition(), controller, intake);
        state.addMotions(sequence.getMotions());
    }

    public void update(boolean userReady) {
        // if drivetrain is available and finished with current
        // motion, and consumer is ready for class to move on
        // to next motion, then do so
        if (state.getDrivetrainController().available() && userReady) {
            Optional<Motion> motion = state.nextMotion();

            // TODO: wait for user input for testing
            if (motion.isPresent()) {
                // dispatch auton state handling to visitor
                motion.get().accept(state);
            }
        }

        // then update controller
        state.update();
    }

    public void update() {
        update(false);
    }
}
