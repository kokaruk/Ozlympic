package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.HashSet;
import java.util.Set;

/**
 * Athlete Class. Extends Game Participant.
 * @author dimz
 * @since 10/3/17
 * @version 2.0
 */
public class GamesAthlete extends GamesParticipant {

    final private AthleteType _athleteType; // type of athlete
    private Integer _totalPoints = 0; // Total point of athlete, acquired in all the games.
    private double _lastGameCompeteTime; // stores last game result
    private Set<OzlParticipation> _participation = new HashSet<>(); // OzlParticipation Association

    /**
     * @param _athleteType type of athlete
     *                     @see AthleteType
     */
    GamesAthlete(String _id, String _name, int _age, String _state, AthleteType _athleteType) {
        super(_id, _name, _age, _state);
        this._athleteType = _athleteType;
    }

    /**
     * Static method to build string for athlete id.
     * @return string of id
     * @param athleteType - type of this athlete
     */
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


    /**
     * Main compete method. Returns a random int in a range stored in GameSport
     * @param _participation
     * @return integer
     * @throws IllegalGameException if trying to compete in a game of which athlete is not a participant
     */
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

    /**
     * Gets score for a game.
     * @param aGame
     * @return
     * @throws IllegalGameException if trying to get score from a game of which athlete is not a participant
     */
    Integer getGameScore(OzlGame aGame) throws IllegalGameException {
        return _participation.stream()
                .filter( p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getScore)
                .orElseThrow( () -> new IllegalGameException(this, aGame));
    }

    //

    /**
     * Gets time result for a game
     * @param aGame
     * @return
     * @throws IllegalGameException if trying to time result from a game of which athlete is not a participant
     */
    Double getGameTime(OzlGame aGame) throws IllegalGameException {
        return _participation.stream()
                .filter( p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getResult)
                .orElseThrow( () -> new IllegalGameException(this, aGame));
    }

}