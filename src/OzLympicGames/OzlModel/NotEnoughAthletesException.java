package OzLympicGames.OzlModel;

/**
 * Thrown if attempting to run a game that doesn't meet minimum players threshold
 *
 * @author dimz
 * @since 27/4/17.
 */
public class NotEnoughAthletesException extends IllegalArgumentException {
    public NotEnoughAthletesException(OzlGame aGame) {
        super(String.format("Not enough players to play the game %s : %s. Only %d Athletes recorded",
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString()),
                aGame.athletesCount())
        );
    }
}
