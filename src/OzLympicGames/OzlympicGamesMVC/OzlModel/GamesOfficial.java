package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GameParticipant implements Referee {

    //constructor
    public GamesOfficial(String participantName, int participantAge) {
        super( participantName,  participantAge);
    }

    // returns score of a game
    public String gameScore(OzlGame myOzlGame) {
        return null;
    }
}
