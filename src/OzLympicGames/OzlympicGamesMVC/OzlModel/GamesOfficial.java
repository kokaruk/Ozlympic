package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimi on 10/3/17.
 * referee class
 */
public class GamesOfficial extends GamesParticipant {

    private OzlGame _game;

    //constructor
    public GamesOfficial(String _id, String _name, int _age, String _state) {
        super(_id, _name, _age, _state);
    }

    // add reference to the game
    public void setGame(OzlGame _game) {
        this._game = _game;
    }

    public void removeGame(){
        _game = null;
    }

    // official awards points to athletes
    void awardPoints(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {

        int maxAwardPoints = 5; // max awards point for a winner

        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) throw new IllegalGameException(this, currentGame);
        // check if game has been played
        if (!currentGame.isGamePlayed()) throw new GameNeverPlayedException(currentGame);
        // get winners by finish time
        // award points
        for (GamesAthlete champion : currentGame.getGameAthletesWinnersSortedByTime()) { // returns three top athletes
            OzlParticipation champGameParticipation = champion.getParticipation().stream()
                    .filter( p -> p.game.equals(currentGame))
                    .findAny()
                    .orElseThrow( () -> new IllegalGameException(champion, currentGame));
            champGameParticipation.score = maxAwardPoints;
            // set overall points of a champion
            champion.setTotalPoints(maxAwardPoints);
            // reduce max points by 2 every iteration
            maxAwardPoints -=2;
        }
    }

    /**
     *
     * returns comma separated list of string of game winners
     * ID, Name, State, Compete Results
     * @param currentGame
     * @return
     * @throws IllegalGameException
     * @throws GameNeverPlayedException
     */
    // get official of the game to call the results
    // simple list of strings, no actual logic in method
    public List<String> getGameScore(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {
        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) throw new IllegalGameException(this, currentGame);
        // check if game has been played
        if (!currentGame.isGamePlayed()) throw new GameNeverPlayedException(currentGame);
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