package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GameParticipant implements Referee {

    //constructor
    public GamesOfficial(String participantName, int participantAge) {
        super( participantName,  participantAge);
    }

    // returns score of a game as a formatted string
    public String gameScore() {
        return null;
    }
}
