package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 */
public class OzlGames {
    private OzlGame[] myOzlGames;

    private IOzlGamesORM dataReaderORM = OzlGamesORMFake.getInstance();

    public OzlGame[] getMyOzlGames() {
        if (myOzlGames.length == 0) myOzlGames = dataReaderORM.getGames();
        return myOzlGames;
    }

    public OzlGames() {
    }
}
