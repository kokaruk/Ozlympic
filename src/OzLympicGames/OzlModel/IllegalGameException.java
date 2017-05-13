package OzLympicGames.OzlModel;

/**
 * Thrown if attemted to invoke methods without established association between classes
 *
 * @author dimz
 * @since 25/4/17.
 */
public class IllegalGameException extends IllegalArgumentException {
    public IllegalGameException(GamesParticipant aParticipant, OzlGame aGame) {
        super(String.format("%s : %s not assigned to game %s : %s ",
                aParticipant.getId(),
                aParticipant.getName(),
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString())));
    }
}
