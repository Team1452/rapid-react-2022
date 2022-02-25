package frc.robot;

/**
 * Data carrier to represent Field position
 * as a grid (in inches) where
 * (0, 0) is bottom-left, (378, 648) is top-right.
 * 
 * Also stores angle relative to positive-x axis from 0 to 2pi (radians)
 */
public class Location {
    private final double x, y;

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
   
    public double getX() { return x; }
    public double getY() { return y; }
}
