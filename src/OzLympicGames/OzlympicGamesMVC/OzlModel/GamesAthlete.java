package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by dimi on 10/3/17.
 * Athlete Class. Extends Game Participant
 */
public class GamesAthlete extends GamesParticipant {

    // type of athlete
    final private AthleteType _athleteType;
    // Total point of athlete, acquired in all the games.
    private Integer _totalPoints = 0; //DEFAULT_POINTS_OF_NEW_ATHLETE;
    private double _lastGameCompeteTime;
    // OzlParticipation Association
    private Set<OzlParticipation> myParticipation = new HashSet<>();

    // constructor with athlete type
    GamesAthlete(String _id, String _name, int _age, String _state, AthleteType _athleteType) {
        super(_id, _name, _age, _state);
        this._athleteType = _athleteType;
    }

    // Getter & Setter
    public Integer getTotalPoints() {
        return _totalPoints;
    }

    void setTotalPoints(Integer totalPoints) {
        this._totalPoints += totalPoints;
    }

    // Getter
    AthleteType getAthleteType() {
        return _athleteType;
    }

    Double getLastGameCompeteTime() {
        return _lastGameCompeteTime;
    }

    public void addParticipation(OzlParticipation aParticipation) {
        myParticipation.add(aParticipation);
    }
    public void removeParticipation(OzlParticipation aParticipation) {
        if (myParticipation.contains(aParticipation)) myParticipation.remove(aParticipation);
    }

    public Set<OzlParticipation> getMyParticipation() {
        return myParticipation;
    }

    // compete method. returns a random int in a preset range
    // based on the game type enumeration
    public double compete(OzlParticipation _participation) throws IllegalGameException {
        if ( myParticipation.contains(_participation)) {
            // this game assigned, compete
            _lastGameCompeteTime = GamesHelperFunctions.getRandomDoubleInRange(
                    _participation.game.getGameSport().getMin(),
                    _participation.game.getGameSport().getMax());
            return _lastGameCompeteTime;
        } else {
            throw new IllegalGameException (this, _participation.game);
        }

    }

    // method to get points for a game
    Integer getGameScore(OzlGame aGame) throws IllegalGameException {
        // see if athlete participates in this game
        return myParticipation.stream()
                .filter( p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getScore)
                .orElseThrow( () -> new IllegalGameException(this, aGame));
    }

}