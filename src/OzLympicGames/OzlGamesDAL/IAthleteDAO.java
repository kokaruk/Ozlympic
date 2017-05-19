package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.GamesAthlete;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Data access layer of Athlete class.
 * @author dimz
 * @since 11//05/17
 */
public interface IAthleteDAO {
    GamesAthlete getNewAthlete(String name, Integer age, String state, String type)
            throws SQLException, ClassNotFoundException, IOException;
}
