package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GamesAthlete extends GamesParticipant implements IGamesAthlete {

    // type of athlete
    private AthleteType athleteType;
    // Getter
    AthleteType getAthleteType() {
        return athleteType;
    }

    // Display total point of athlete, acquired in all the games.
    private static Integer DEFAULT_POINTS_OF_NEW_ATHLETE = 0;
    private Integer totalPoints = DEFAULT_POINTS_OF_NEW_ATHLETE;
    // Getter & Setter
    Integer getTotalPoints() { return totalPoints;  }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints += totalPoints; }

    private int lastGameCompeteTime;
    public Integer getLastGameCompeteTime() { return lastGameCompeteTime; }

    // constructor
    GamesAthlete(String participantName, int participantAge, String participantState) {
        super(participantName, participantAge, participantState);
        this.athleteType = generateAthleteType();
    }

    // compete method. returns a random int in a preset range
    // based on the game type enumeration
    @Override
    public int compete() throws MyOzlGameNotDefinedException {
        if (getMyOzlGame() == null){
            throw new MyOzlGameNotDefinedException("Games Athlete not Assigned to a Game, Can't compete yet");
        }
        lastGameCompeteTime = GamesSharedFunctions.getRandomNumberInRange(
                              getMyOzlGame().getGameSportType().getMin(),
                              getMyOzlGame().getGameSportType().getMax());
        return lastGameCompeteTime;
    }

    // method to randomise athlete type
    private AthleteType generateAthleteType(){
        int randomNumber = GamesSharedFunctions.getRandomNumberInRange(0, AthleteType.values().length-1);
        return AthleteType.values()[randomNumber];
    }
}
