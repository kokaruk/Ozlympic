package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimz on 27/4/17.
 */
public class WrongSportException extends IllegalArgumentException {
    public WrongSportException(GamesParticipant aParticipant, OzlGame aGame){
        super(String.format("Wrong Sport Assignment. %s : %s not assigned to game %s : %s ",
                aParticipant.getId(),
                aParticipant.getName(),
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString())));
    }
}

