package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.GamesAthlete;
import OzLympicGames.OzlModel.GamesOfficial;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 19/5/17.
 */
public interface IGamesDAL {

    GamesAthlete getNewAthlete(String name, Integer age, String state, String type)
            throws SQLException, ClassNotFoundException, IOException;
    GamesOfficial getNewReferee(String name, Integer age, String state)
            throws SQLException, ClassNotFoundException, IOException;
}
