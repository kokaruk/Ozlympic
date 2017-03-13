package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesAthlete extends GameParticipant implements SportsPerson {
    private AthleteType athleteType;

    // Getter, lazy instantiate athlete type, avoids super constructor override
    public AthleteType getAthleteType() {
        if (this.athleteType == null) this.athleteType = generateAthleteType();
        return athleteType;
    }

    // Display total point of athlete, acquired in all the games.
    private Integer totalPoints = 0;
    // Getter
    public Integer getTotalPoints() {
        return totalPoints;
    }

    // constructor
    public GamesAthlete(String participantName, int participantAge) { super(participantName, participantAge); }

    // compete method. returns a random int in a preset range
    // based on the athlete type.
    public int compete() {
        return 0;
    }

    private AthleteType generateAthleteType(){
        return null;
    }

}
