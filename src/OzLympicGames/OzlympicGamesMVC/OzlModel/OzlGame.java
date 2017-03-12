package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class OzlGame {
    private GameSports gameSports;
    private GameParticipant[] participants = new GameParticipant[8];
    private String gameId;
    private static int minParticipants = 4;
    private int gameScore;

    public OzlGame(){
     ;
    }
}
