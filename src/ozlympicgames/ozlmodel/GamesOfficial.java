package ozlympicgames.ozlmodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Referee class.
 *
 * @author dimz
 * @since 10/3/17
 */
public class GamesOfficial extends GamesParticipant {

    private static Logger logger = LogManager.getLogger();
    private OzlGame _game;

    /**
     * Public constructor, passes all parameters to parent superclass.
     */
    public GamesOfficial(Integer _id, String _name, int _age, String _state) {
        super(_id, _name, _age, _state);
    }

    @Override
    public String getId() {
        return String.format("%s%04d", "RE", get_id());
    }

    // add reference to the game

    /**
     * Creates one-to-one Referee &lt;=&gt; Game association.
     *
     * @param _game Add instance of game to the instance.
     */
    public void setGame(OzlGame _game) {
        this._game = _game;
    }

    public void removeGame() {
        _game = null;
    }

    /**
     * Official awards points to athletes
     *
     * @param currentGame requires instance of game
     * @throws IllegalGameException     when passed instance of game is not in association with instance of official
     * @throws GameNeverPlayedException when attempts to award points to a new game
     * @see OzlGame
     */
    void awardPoints(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {

        int maxAwardPoints = 5; // max awards point for a winner

        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) {
            logger.error("IllegalGameException thrown " + this.getId());
            throw new IllegalGameException(this, currentGame);
        }
        // check if game has been played
        if (!currentGame.isGamePlayed()) {
            logger.error("GameNeverPlayedException thrown " + this.getId());
            throw new GameNeverPlayedException(currentGame);
        }
        // get winners by finish time
        // award points
        for (GamesAthlete champion : currentGame.getGameAthletesWinnersSortedByTime()) { // returns three top athletes
            OzlParticipation champGameParticipation = champion.getParticipation().stream()
                    .filter(p -> p.game.equals(currentGame))
                    .findAny()
                    .orElseThrow(() -> new IllegalGameException(champion, currentGame));
            champGameParticipation.score = maxAwardPoints;
            // set overall points of a champion
            champion.setTotalPoints(maxAwardPoints);
            // reduce max points by 2 every iteration
            maxAwardPoints -= 2;
        }
    }

    /**
     * get official of the game to call the results
     * ID, Name, State, Compete Results
     *
     * @param currentGame requires instance of game
     * @return list of CSV-string of game winners
     * @throws IllegalGameException     when passed instance of game is not in association with instance of official
     * @throws GameNeverPlayedException when attempts to build results from a new game
     */
    // simple list of strings, no actual logic in method
    public List<String> getGameScore(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {
        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) {
            logger.error("IllegalGameException thrown " + this.getId());
            throw new IllegalGameException(this, currentGame);
        }
        // check if game has been played
        if (!currentGame.isGamePlayed()) {
            logger.error("GameNeverPlayedException thrown " + this.getId());
            throw new GameNeverPlayedException(currentGame);
        }
        List<String> winnersScore = new ArrayList<>();
        StringBuilder winnersResult;
        for (GamesAthlete champion : currentGame.getGameAthletesWinnersSortedByTime()) {
            winnersResult = new StringBuilder();
            winnersResult.append(champion.getId().concat(","));
            winnersResult.append(champion.getName().concat(","));
            winnersResult.append(champion.getState().concat(","));
            winnersResult.append(String.format("%.2f", champion.getGameTime(currentGame)).concat(","));
            winnersResult.append(champion.getGameScore(currentGame).toString().concat(","));
            winnersResult.append(GamesHelperFunctions.firsLetterToUpper(
                    String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))).concat(","));
            winnersScore.add(winnersResult.toString());
        }
        return winnersScore;
    }


}