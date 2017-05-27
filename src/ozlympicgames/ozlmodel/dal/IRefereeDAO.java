package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesOfficial;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author dimz
 * @since 19/5/17.
 */
public interface IRefereeDAO {
    GamesOfficial getNewReferee(String name, Integer age, String state)
            throws SQLException, ClassNotFoundException, IOException;

    Map<String, GamesOfficial> getOfficialsMap()
            throws SQLException, ClassNotFoundException;
}
