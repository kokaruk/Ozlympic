package ozlympicgames.ozlmodel;

/**
 * Association class between a participant (Official or Athlete) and a Game.
 * Making fields protected so can access fields directly as this is essentially a struct-like class
 * No need to encpsualte fields
 *
 * @author dimz
 * @since 24/4/17.
 */
public class OzlParticipation {

    /**
     * Association fields. Declare them all accessible,
     * as this is essentially a struct
     */
    OzlGame game;
    Double result = 0.0; // time
    Integer score = 0; // game points
    GamesAthlete gamesAthlete;

    // constructor
    public OzlParticipation(GamesAthlete gamesAthlete, OzlGame game) {
        this.gamesAthlete = gamesAthlete;
        this.game = game;
    }

    public OzlParticipation(GamesAthlete gamesAthlete, OzlGame game, Integer score, Double result) {
        this(gamesAthlete, game);
        this.score = score;
        this.result = result;
    }

    //getters (needed for stream object methods
    public GamesAthlete getGamesAthlete() {
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
