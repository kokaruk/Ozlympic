package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GameParticipant implements Referee {
    public GamesOfficial(String participantId, String participantName, int participantAge)  {
        super( participantId,  participantName,  participantAge);
    }

    public int gameScore() {
        return 0;
    }
}
