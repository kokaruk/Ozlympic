package OzLympicGames.OzlModel;

/**
 * Exception if Attempted to Assign Participant to Unknown State
 *
 * @author dimz
 * @since 25/4/17.
 */
public class IllegalAustralianStateException extends IllegalArgumentException {

    public IllegalAustralianStateException(String state) {
        super("Unknown Au State: " + state);
    }

}
