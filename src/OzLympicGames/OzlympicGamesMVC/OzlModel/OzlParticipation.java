package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimz on 24/4/17.
 * Association class between a participant (Official or Athlete)
 * and a Game.
 */
public class OzlParticipation {

    public OzlParticipation(GamesParticipant gamesParticipant, OzlGame game) {
        this.gamesParticipant = gamesParticipant;
        this.game = game;
    }

    /**
     * Association fields. Declare them all accessible,
     * as this is essentially a struct
     */
    private GamesParticipant gamesParticipant;
    public OzlGame game;
    public Double result;
    public Integer score;

    GamesParticipant getGamesParticipant() {
        return gamesParticipant;
    }
}
