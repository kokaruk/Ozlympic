package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesAthlete extends GameParticipant implements SportsPerson {
    private AthleteType athleteType;
    private int totalPoints;
    private int gamePoints;

    public GamesAthlete(String participantId, String participantName, int participantAge) {
        super( participantId,  participantName,  participantAge);
    }

    public int compete() {
        return 0;
    }
}
