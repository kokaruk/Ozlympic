package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GamesParticipant implements IGamesOfficial {

    //constructor
    GamesOfficial(String participantName, int participantAge, String participantId, String participantState) {
        super( participantName,  participantAge, participantState);
        this.setParticipantId(participantId);
    }

    // returns score of a game as a formatted string
    @Override
    public String gameScore() {
        return null;
    }
}

