package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.Arrays;


/**
 * Created by dimi on 10/3/17.
 */
class OzlGame {

    //property with private getter for configuration reader.
    //Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader;
    public void setConfigReader(IOzlConfigRead configReader) {
        this.configReader = configReader;
    }

    // gameSportType of enum
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
    public OzlGame(String gameId) {
        // Game ID, to be set by games
        this.gameId = gameId;
        configReader = OzlConfigRead.getInstance();
        minParticipants = configReader.getConfigInt("minParticipants");
    }

    // Method to Generate GameSports enum based on ID string
    private GameSports generateSport(String gameId)  {
        String sportsLetter = gameId.substring(0,1).toLowerCase();
        GameSports mySportWrapper = Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().get();

        return mySportWrapper;

    }

}
