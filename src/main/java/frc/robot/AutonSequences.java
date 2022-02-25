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
        final Location leftTerminal = new Location(34.967, 47.803);        
        final Location leftTopBall = new Location(209.497, 242.643);
        final Location leftBottomBall = new Location(198, 74);

        final Location rightTerminal = new Location(34.967, 47.803);        
        final Location rightTopBall = new Location(209.497, 242.643);
        final Location rightBottomBall = new Location(452.959, 79.499);

        sequences.put(
            Tarmac.LEFT_BOTTOM, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  leftBottomBall,
                /* terminal */  leftTerminal
            )
        );

        sequences.put(
            Tarmac.LEFT_MIDDLE, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  leftTopBall,
                /* terminal */  leftTerminal
            )
        );

        sequences.put(
            Tarmac.LEFT_TOP, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  leftTopBall,
                /* terminal */  leftTerminal
            )
        );

        sequences.put(
            Tarmac.RIGHT_BOTTOM, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  rightBottomBall,
                /* terminal */  rightTerminal
            )
        );

        sequences.put(
            Tarmac.RIGHT_MIDDLE, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  rightTopBall,
                /* terminal */  rightTerminal
            )
        );

        sequences.put(
            Tarmac.RIGHT_TOP, 
            new AutonSequence(
                /* start    */  new Position(new Location(0, 0), 0),
                /* ball     */  rightTopBall,
                /* terminal */  rightTerminal
            )
        );
    }
}
