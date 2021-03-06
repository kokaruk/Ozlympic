package ozlympicgames.ozlmodel;

import java.util.HashSet;
import java.util.Set;

/**
 * Athlete Class.
 *
 * @author dimz
 * @version 2.0
 * @since 10/3/17
 */
public class GamesAthlete extends GamesParticipant {

    final private AthleteType _athleteType; // type of athlete
    private Integer _totalPoints = 0; // Total point of athlete, acquired in all the games.
    private double _lastGameCompeteTime; // stores last game result
    private Set<OzlParticipation> _participation = new HashSet<>(); // OzlParticipation Association

    /**
     * Public constructor with athlete type.
     *
     * @param _athleteType type of athlete
     * @see AthleteType
     */
    public GamesAthlete(Integer _id, String _name, int _age, String _state, String _athleteType) {
        super(_id, _name, _age, _state);
        this._athleteType = AthleteType.valueOf(_athleteType);
    }

    public GamesAthlete(Integer _id, String _name, int _age, String _state, String _athleteType, int _totalPoints, double _lastGameCompeteTime) {
        this(_id, _name, _age, _state, _athleteType);
        this._totalPoints = _totalPoints;
        this._lastGameCompeteTime = _lastGameCompeteTime;
    }

    /**
     * Static method to build prefix for athlete id.
     *
     * @param athleteType - type of this athlete
     * @return string of id
     */
    public static String idPrefix(AthleteType athleteType) {
        return athleteType.name().substring(0, 2).toUpperCase();
    }

    @Override
    public String getId() {
        return String.format("%s%04d", idPrefix(_athleteType), get_id());
    }

    /**
     * Overall points in all games
     *
     * @return totalPoints property
     */
    public Integer getTotalPoints() {
        return _totalPoints;
    }

    public void setTotalPoints(Integer gamePoints) {
        this._totalPoints += gamePoints;
    }

    // Getter
    public AthleteType getAthleteType() {
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
     *
     * @param _participation requires an instance of participation class
     * @return random integer within bounds set in sport parameter
     * @throws IllegalGameException if trying to compete in a game of which athlete is not a participant
     * @see OzlParticipation
     */
    public double compete(OzlParticipation _participation) throws IllegalGameException {
        if (this._participation.contains(_participation)) {
            // this game assigned, compete
            _lastGameCompeteTime = GamesHelperFunctions.getRandomDoubleInRange(
                    _participation.game.getGameSport().getMin(),
                    _participation.game.getGameSport().getMax());
            return _lastGameCompeteTime;
        } else {
            throw new IllegalGameException(this, _participation.game);
        }

    }

    /**
     * Gets score for a game.
     *
     * @param aGame requires an instance of Game
     * @return integer points acquired in the game
     * @throws IllegalGameException if trying to get score from a game of which athlete is not a participant
     * @see OzlGame
     */
    Integer getGameScore(OzlGame aGame) throws IllegalGameException {
        return _participation.stream()
                .filter(p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getScore)
                .orElseThrow(() -> new IllegalGameException(this, aGame));
    }

    /**
     * Gets time result for a game.
     *
     * @param aGame requires an instance of Game
     * @return double, time results in the game
     * @throws IllegalGameException if trying to time result from a game of which athlete is not a participant
     */
    Double getGameTime(OzlGame aGame) throws IllegalGameException {
        return _participation.stream()
                .filter(p -> p.game.equals(aGame))
                .findAny()
                .map(OzlParticipation::getResult)
                .orElseThrow(() -> new IllegalGameException(this, aGame));
    }

}