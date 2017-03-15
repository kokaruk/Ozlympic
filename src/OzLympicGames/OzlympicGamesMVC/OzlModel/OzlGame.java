package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.Arrays;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Created by dimi on 10/3/17.
 */
class OzlGame {
    // gamesportType of enum
    private GameSports gameSportType;
    // public getter with lazy instantiation
    public GameSports getGameSportType() {
        if (this.gameSportType == null) this.gameSportType = generateSport(gameId);
        return gameSportType;
    }
    // array of game participants. participant at index 0 is always the official and getter/setter
    private GameParticipant[] participants = new GameParticipant[8];
    public GameParticipant[] getParticipants() {
        return participants;
    }
    public void setParticipants(GameParticipant[] participants) {
        this.participants = participants;
    }

    // game ID. readonly getter
    private String gameId;
    public String getGameId() {
        return gameId;
    }
    // minimum participants in a game, sate from config file, populate at init, and getter
    private final int minParticipants;
    public int getMinParticipants() {
        return minParticipants;
    }

    private int gameScore;

    // Constructor
    public OzlGame(String gameId){
        // Game ID, to be set by games
        this.gameId = gameId;
        this.minParticipants = this.minParticipants();
    }
    // read properties file and set final value for minimum participants
    private int minParticipants(){
        Properties myProp = new Properties();
        InputStream in = getClass().getResourceAsStream("config.properties");
        int minParticipants=0;
        try {
            myProp.load(in);
            in.close();
            minParticipants = Integer.parseInt (
                                     myProp.getProperty("minParticipants"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return minParticipants;
    }

    // Method to Generate GameSports member based on based on ID string
    private GameSports generateSport(String gameId)  {
        String sportsLetter = gameId.substring(0,1).toLowerCase();
        GameSports mySportWrapper = Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().get();

        return mySportWrapper;

    }

}
