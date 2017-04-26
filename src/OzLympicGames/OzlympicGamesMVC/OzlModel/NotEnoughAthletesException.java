package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * @author dimz
 * @since 27/4/17.
 */
public class NotEnoughAthletesException extends IllegalArgumentException {
    public NotEnoughAthletesException(OzlGame aGame){
        super(String.format("Not enough players to play the game %s : %s. Only %d Athletes recorded",
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString()),
                GamesHelperFunctions.athletesCount(aGame))

        );
    }
}
