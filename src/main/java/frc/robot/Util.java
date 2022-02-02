package frc.robot;

public class Util {

    public static double state(boolean forward, boolean backward) {
        // returns an integer representing direction
        double direction = 0;
        if (forward)
            direction++;

        if (backward)
            direction--;
        else
            direction = 0;

        return direction;

    }

}
