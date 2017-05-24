package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.OzlGame;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author dimz
 * @since 19/5/17.
 */
public interface IGameDAO {
    OzlGame getNewGame(String sport)
            throws SQLException, ClassNotFoundException, IOException;
    Map<String,OzlGame> getGamesMap()throws SQLException, ClassNotFoundException;
    void updateGame(OzlGame game, String timestamp)throws SQLException, ClassNotFoundException;
}
