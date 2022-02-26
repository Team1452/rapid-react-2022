package frc.robot.auton;

import java.util.List;

import frc.robot.Position;

public class AutonSequence {
    private List<Motion> motions;
    private Position startingPosition;

    public AutonSequence(Position startingPosition, List<Motion> motions) {
        this.startingPosition = startingPosition; 
        this.motions = motions;
    }

    public List<Motion> getMotions() { return motions; }
    public Position getStartingPosition() { return startingPosition; }
}
