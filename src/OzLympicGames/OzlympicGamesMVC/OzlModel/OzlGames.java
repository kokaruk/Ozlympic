package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 */
public class OzlGames {
    private OzlGame[] myOzlGames;
    public OzlGame[] getMyOzlGames() {
        if (myOzlGames.length == 0) myOzlGames = OzlGamesORM.getGames();
        return myOzlGames;
    }

    public OzlGames() {
    }
}
