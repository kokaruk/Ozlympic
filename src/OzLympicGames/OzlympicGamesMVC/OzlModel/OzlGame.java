package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


/**
 * Created by dimi on 10/3/17.
 */
class OzlGame {
    private GameSports gameSports;
    public GameSports getGameSports() {
        return gameSports;
    }


    private GameParticipant[] participants = new GameParticipant[8];
    private String gameId;
    private static int minParticipants = 4;
    private int gameScore;

    // Constructor
    public OzlGame(String gameId){
        // Game ID, to be set by games
        this.gameId = gameId;
        this.gameSports = generateSport(gameId);

    }

    // Method to Generate GameSports based on ID string
    private GameSports generateSport(String gameId)  {
        String sportsLetter = gameId.substring(0,1).toLowerCase();
        GameSports mySportWrapper = Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().get();

        return mySportWrapper;

    }

}
