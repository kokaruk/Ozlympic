package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 * this class will speak to dataLayer which should return data from DataSource.
 * As Current Project Scope Doesn't require access to XML ot DataBase, this class will contain hardcoded data to
 * generate some bogus data for games
 */
final class OzlGamesORM {

    private OzlGamesORM() {}

    public static OzlGame[] getGames(){

        // some logic that reads from database from initial game start,
        // if nothing found generates random 20 games

        OzlGame myGame = new OzlGame();
        return new OzlGame[]{myGame};
    }



}
