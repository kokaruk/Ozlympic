package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 * Athlete Class. Extends Game Participant
 */
class GamesAthlete extends GamesParticipant implements IGamesAthlete {

    // type of athlete
    final private AthleteType athleteType;
    // Getter
    AthleteType getAthleteType() {
        return athleteType;
    }

    // Display total point of athlete, acquired in all the games.
    private final Integer DEFAULT_POINTS_OF_NEW_ATHLETE = 0;
    private Integer totalPoints = DEFAULT_POINTS_OF_NEW_ATHLETE;
    // Getter & Setter
    Integer getTotalPoints() { return totalPoints;  }
    void setTotalPoints(Integer totalPoints) { this.totalPoints += totalPoints; }

    private double lastGameCompeteTime;
    double getLastGameCompeteTime() { return lastGameCompeteTime; }

    // constructor
    GamesAthlete(String participantName, int participantAge, String participantState) {
        super(participantName, participantAge, participantState);
        this.athleteType = generateRandomAthleteType();
    }

    // compete method. returns a random int in a preset range
    // based on the game type enumeration
    @Override
    public double compete() {
        if (getMyOzlGame() != null){
            // game assigned, compete
            lastGameCompeteTime = GamesHelperFunctions.getRandomDoubleInRange(
                    getMyOzlGame().getGameSportType().getMin(),
                    getMyOzlGame().getGameSportType().getMax());
        }
        return lastGameCompeteTime;
    }

    // method to randomise athlete type at the instantiation for more fun
    private AthleteType generateRandomAthleteType(){
        int randomNumber = Math.round (GamesHelperFunctions.getRandomNumberInRange(0, AthleteType.values().length-1));
        return AthleteType.values()[randomNumber];
    }
}