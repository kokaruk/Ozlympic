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
        // check if game has been played
        if (!currentGame.isReplay()) throw new GameNeverPlayedException(currentGame);
        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) throw new IllegalGameException(this, currentGame);
        // get winners by finish time
        List<GamesAthlete> gameWinners = currentGame.getGameAthletesWinnersSortedByTime();
        // award points
        int[] awardPoints = new int[]{5, 2, 1};
        for (int i = 0; i < awardPoints.length; i++) {
            (gameWinners.get(i)).setTotalPoints(awardPoints[i]);
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
    public List<String> getGameScore(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {
        // see if assigned to a game or call from a correct game
        if (_game == null || !_game.equals(currentGame)) throw new IllegalGameException(this, currentGame);
        // check if game has been played
        if (!currentGame.isReplay()) throw new GameNeverPlayedException(currentGame);
        List<String> winnersScore = new ArrayList<>();
        List<GamesAthlete> gameWinners = currentGame.getGameAthletesWinnersSortedByTime();
        StringBuilder winnersResult;
        for (GamesAthlete champion : gameWinners) {
            winnersResult = new StringBuilder();
            winnersResult.append(champion.getId().concat(","));
            winnersResult.append(champion.getName().concat(","));
            winnersResult.append(champion.getState().concat(","));
            winnersResult.append(String.format("%.2f", champion.getLastGameCompeteTime()).concat(","));
            winnersResult.append(champion.getTotalPoints().toString().concat(","));
            winnersResult.append(GamesHelperFunctions.firsLetterToUpper(
                    String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))).concat(","));
            winnersScore.add(winnersResult.toString());
        }
        return winnersScore;
    }





}