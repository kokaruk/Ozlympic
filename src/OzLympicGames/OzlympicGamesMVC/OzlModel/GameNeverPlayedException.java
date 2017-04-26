package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * @author dimz
 * @since 25/4/17.
 */
public class GameNeverPlayedException extends IllegalArgumentException {
    public GameNeverPlayedException (OzlGame aGame) {
        super(String.format("This game never run %s : %s",
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString())));
    }
}
