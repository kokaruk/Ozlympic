package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
final class GamesAthlete extends GameParticipant implements SportsPerson {

    // type of athlete
    private AthleteType athleteType;
    // Getter
    public AthleteType getAthleteType() {
        return athleteType;
    }

    // Display total point of athlete, acquired in all the games.
    // Zero (0) at init time
    private Integer totalPoints = 0;
    // Getter & Setter
    public Integer getTotalPoints() { return totalPoints;  }
    public void setTotalPoints(Integer totalPoints) { this.totalPoints = totalPoints; }

    // constructor
    public GamesAthlete(String participantName, int participantAge) {
        super(participantName, participantAge);
        this.athleteType = generateAthleteType();
    }

    // compete method. returns a random int in a preset range
    // based on the game type enumeration
    public int compete() throws MyOzlGameNotDefinedException {
        if (super.getMyOzlGame() == null){
            throw new MyOzlGameNotDefinedException("Games Athlete not Assigned to a Game, Can't compete yet");
        }
        int competeTime = GamesSharedFunctions.getRandomNumberInRange(
                            super.getMyOzlGame().getGameSports().getMin(),
                            super.getMyOzlGame().getGameSports().getMax()
        );
        return competeTime;
    }

    // method to randomise athlete type
    private AthleteType generateAthleteType(){
        int randomNumber = GamesSharedFunctions.getRandomNumberInRange(0, AthleteType.values().length-1);
        return AthleteType.values()[randomNumber];
    }
}
