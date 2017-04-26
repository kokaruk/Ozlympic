package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * @author dimz
 * @since 27/4/17.
 */
public class NoRefereeException extends IllegalArgumentException {
    public NoRefereeException(OzlGame aGame){
        super(String.format("No referee in game %s : %s",
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString()))
        );
    }
}
