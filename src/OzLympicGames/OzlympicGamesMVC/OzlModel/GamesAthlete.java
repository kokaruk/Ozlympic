package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by dimi on 10/3/17.
 * Athlete Class. Extends Game Participant
 */
public class GamesAthlete extends GamesParticipant {

    // type of athlete
    final private AthleteType _athleteType;
    // Total point of athlete, acquired in all the games.
    private Integer _totalPoints = 0;
    private double _lastGameCompeteTime;
    // OzlParticipation Association
    private Set<OzlParticipation> _participation = new HashSet<>();

    // constructor with athlete type
    GamesAthlete(String _id, String _name, int _age, String _state, AthleteType _athleteType) {
        super(_id, _name, _age, _state);
        this._athleteType = _athleteType;
    }

    //
    static String idPrefix(AthleteType athleteType){
        return athleteType.name().substring(0, 3).toUpperCase();
    }

    // Getter & Setter
    public Integer getTotalPoints() {
        return _totalPoints;
    }

    void setTotalPoints(Integer gamePoints) {
        this._totalPoints += gamePoints;
    }

    // Getter
    AthleteType getAthleteType() {
        return _athleteType;
    }

    Double getLastGameCompeteTime() {
        return _lastGameCompeteTime;
    }

    public void addParticipation(OzlParticipation aParticipation) {
        _participation.add(aParticipation);
    }
    public void removeParticipation(OzlParticipation aParticipation) {
        if (_participation.contains(aParticipation)) _participation.remove(aParticipation);
    }

    public Set<OzlParticipation> getParticipation() {
        return _participation;
    }

    // compete method. returns a random int in a preset range
    // based on the game type enumeration
    public double compete(OzlParticipation _participation) throws IllegalGameException {
        if ( this._participation.contains(_participation)) {
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
        return _participation.stream()
                .filter( p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getScore)
                .orElseThrow( () -> new IllegalGameException(this, aGame));
    }

    // method to get points for a game
    Double getGameTime(OzlGame aGame) throws IllegalGameException {
        return _participation.stream()
                .filter( p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getResult)
                .orElseThrow( () -> new IllegalGameException(this, aGame));
    }

}