package frc.robot.auton;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import frc.robot.Position;
import frc.subsystems.DrivetrainController;
import frc.subsystems.Intake;

/**
 * Encapsulates state Auton needs for 
 * controlling actuators and keeping track
 * of robot position
 */
public class AutonState {
    private Position position;
    private final DrivetrainController controller; 
    private final Intake intake;

    // store motion queue
    private final Queue<Motion> motions = new LinkedList<>();

    public AutonState(Position position, DrivetrainController controller, Intake intake) {
        this.position = position;
        this.controller = controller;
        this.intake = intake;
    }

    public DrivetrainController getDrivetrainController() { return controller; }
    public Intake getIntake() { return intake; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    public Optional<Motion> nextMotion() {
        final Motion result = motions.remove();
        return result == null ? Optional.empty() : Optional.of(result);
    }

    public void addMotion(Motion motion) {
        motions.add(motion);
    }

    public void addMotions(Collection<Motion> motions) {
        this.motions.addAll(motions);
    }

    public void update() {
        // update drivetrain (0, 0 for no user input)
        controller.update(0, 0);
    }
}
