package frc.robot;

/**
 * Data carrier to represent Field position
 * as a grid (in inches) where
 * (0, 0) is bottom-left, (378, 648) is top-right.
 * 
 * Also stores angle relative to positive-x axis from 0 to 2pi (radians)
 */
public class Position {
    private Location location;
    private double angle;

    public Position(Location location, double angle) {
        this.location = location;
        this.angle = angle;
    }
   
    public Location getLocation() { return location; }
    public double getAngle() { return angle; }

    public void setLocation(Location location) { this.location = location; }
    public void setAngle(double angle) { this.angle = angle; }
}
