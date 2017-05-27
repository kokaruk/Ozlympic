package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesAthlete;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * Data access layer of Athlete class.
 *
 * @author dimz
 * @since 11//05/17
 */
public interface IAthleteDAO {
    GamesAthlete getNewAthlete(String name, Integer age, String state, String type)
            throws SQLException, ClassNotFoundException, IOException;

    Map<String, GamesAthlete> getAthletesMap()
            throws SQLException, ClassNotFoundException;

    void updateAthlete(GamesAthlete athlete, String totalpoints) throws SQLException, ClassNotFoundException;
}
