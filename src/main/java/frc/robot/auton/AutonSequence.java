package frc.robot.auton;

import java.util.Arrays;
import java.util.List;

import frc.robot.Location;
import frc.robot.Position;

public class AutonSequence {
    private List<Motion> motions;
    private Position startingPosition;

    public AutonSequence(Position startingPosition, List<Motion> motions) {
        this.startingPosition = startingPosition; 
        this.motions = motions;
    }

    public AutonSequence(double startX, double startY, double startRad, Motion... motions) {
        this.startingPosition = new Position(new Location(startX, startY), startRad);
        this.motions.addAll(Arrays.asList(motions));
    }

    public List<Motion> getMotions() { return motions; }
    public Position getStartingPosition() { return startingPosition; }
}
