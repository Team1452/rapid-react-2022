package frc.robot.auton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

import frc.robot.Location;
import frc.robot.Position;
import frc.robot.Tarmac;
import frc.robot.auton.Motion.MoveTo;

/**
 * Constant global data carrier to hold auton sequences/starting positions
 */
public class AutonSequences {
    // TODO: add in starting positions for each tarmac (reference arena spec in Slack)
    private static final EnumMap<Tarmac, AutonSequence> sequences = new EnumMap<>(Tarmac.class);

    public static AutonSequence getSequence(Tarmac tarmac) {
        return sequences.get(tarmac);
    }

    static {
        sequences.put(
            Tarmac.LEFT_BOTTOM,
            new AutonSequence(
                // starting x, y, degrees
                20.5, 20.6, Math.toRadians(38.5),

                // motions
                new Motion.SetIntake(true),
                new Motion.MoveTo(new Location(198, 74)),
                new Motion.MoveTo(new Location(324, 150)),
                new Motion.LiftAndShoot()
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM,
            new AutonSequence(
                // starting x, y, degrees
                20.5, 20.6, Math.toRadians(38.5),

                // motions
                new Motion.SetIntake(true),
                new Motion.MoveTo(new Location(198, 74)),
                new Motion.MoveTo(new Location(324, 150)),
                new Motion.LiftAndShoot()
            )
        );

        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* TODO */ new Position(new Location(0, 0), 0),
                Arrays.asList(
                    new Motion.SetIntake(true),
                    new Motion.MoveTo(new Location(198, 74)),
                    new Motion.MoveTo(new Location(324, 150)),
                    new Motion.LiftAndShoot()
                )
            )
        );
    }
}

/*
25 ft 6 inch circle
*/
