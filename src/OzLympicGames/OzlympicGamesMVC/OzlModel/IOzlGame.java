package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 19/3/17.
 * - Each game has a unique game ID
 * - There are more than 4 participants.
 * - A user can predict the winner for each game
 */
interface IOzlGame {
    String getGameId();
    int getMinParticipants();
    void setUserPrediction(int userPrediction);
}
