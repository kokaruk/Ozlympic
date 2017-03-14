package OzLympicGames.OzlympicGamesMVC.OzlModel;

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

    }

    // Method to Generate GameSports based on ID string
    private GameSports generateSport(String gameId){
        String sportsLetter = gameId.substring(0,0);

        for (GameSports sports : GameSports.values()) {
            // do what you want
        }


        return null;
    }

}
