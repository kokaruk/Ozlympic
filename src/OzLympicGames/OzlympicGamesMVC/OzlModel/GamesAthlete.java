package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesAthlete extends GameParticipant implements SportsPerson {
    private AthleteType athleteType;
    private int totalPoints;

    public GamesAthlete( String participantName, int participantAge) {
        super(  participantName,  participantAge);
        this.athleteType = generateAthleteType();
    }

    public int compete() {
        return 0;
    }

    private AthleteType generateAthleteType(){
        return null;
    }

}
