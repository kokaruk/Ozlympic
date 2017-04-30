package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimz on 24/4/17.
 * Association class between a participant (Official or Athlete)
 * and a Game.
 */
public class OzlParticipation {

    /**
     * Association fields. Declare them all accessible,
     * as this is essentially a struct
     */
    OzlGame game;
    Double result; // time
    Integer score; // game points
    GamesAthlete gamesAthlete;

    // constructor
    public OzlParticipation(GamesAthlete gamesAthlete, OzlGame game) {
        this.gamesAthlete = gamesAthlete;
        this.game = game;
    }

    //getters (needed for stream object methods
    GamesAthlete getGamesAthlete() {
        return gamesAthlete;
    }
    public OzlGame getGame() {
        return game;
    }
    public Double getResult() {
        return result;
    }
    public Integer getScore() {
        return score;
    }
}
