package frc.robot;

import java.util.EnumMap;

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
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );

        sequences.put(
            Tarmac.LEFT_MIDDLE, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );

        sequences.put(
            Tarmac.LEFT_TOP, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );

        sequences.put(
            Tarmac.RIGHT_BOTTOM, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );

        sequences.put(
            Tarmac.RIGHT_MIDDLE, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );

        sequences.put(
            Tarmac.RIGHT_TOP, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  new Location(0, 0),
                /* terminal */  new Location(0, 0)
            )
        );
    }
}
