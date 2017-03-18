package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GameParticipant implements Referee {

    //constructor
    public GamesOfficial(String participantName, int participantAge, String participantId) {
        super( participantName,  participantAge, participantId);
    }

    // returns score of a game as a formatted string
    @Override
    public String gameScore() {
        return null;
    }
}

