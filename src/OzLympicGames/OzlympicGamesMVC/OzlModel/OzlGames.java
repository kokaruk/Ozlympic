package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 */
public class OzlGames {
    private OzlGame[] myOzlGames;

    public OzlGames() {
        myOzlGames = OzlGamesORM.getGames();
    }
}
