package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.OzlGame;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class GameDAO implements IGameDAO {




    private final static String SQLContext = "GAMES";
    private SQLPreBuilder preBuilder = new SQLPreBuilder(SQLContext);

    // singleton instance
    private static IGameDAO instance;

    // private constructor
    private GameDAO() {}

    // lazy instantiation
    public static IGameDAO getInstance() {
        if (instance == null) {
            instance = new GameDAO();
        }
        return instance;
    }

    @Override
    public OzlGame getNewGame(String sport)
            throws SQLException, ClassNotFoundException, IOException {
        Integer idNum = preBuilder.getNewIdNum(sport);
        OzlGame game = new OzlGame(String.format("%s%04d",sport.substring(0,2).toUpperCase(), idNum));
        String ID = game.getId();
        preBuilder.appendCSV(ID, sport);
        return game;
    }

    @Override
    public Map<String,OzlGame> getGamesMap() throws SQLException, ClassNotFoundException {
        CachedRowSet rs = preBuilder.getRowSetFromView("", "");
        Map<String,OzlGame> gameMap = new HashMap<>();
        while (rs != null && rs.next()) {
            OzlGame game = new OzlGame(rs.getString(1));
            String gameTimestamp = rs.getString(3);
            if ( gameTimestamp != null) {
                game.setTimeRun( Timestamp.valueOf(gameTimestamp));
            }
            gameMap.put(game.getId(), game);
        }
        return gameMap;
    }

    @Override
    public void updateGame(OzlGame game, String timestamp)throws SQLException, ClassNotFoundException {
        SQLPreBuilder gameUpdatePreBuilder = new SQLPreBuilder("GAMEUPDATE");
        Integer id =  Integer.parseInt(game.getId().substring(2));
        gameUpdatePreBuilder.updateRow( id.toString() ,
                "TIMERUN", timestamp);

    }





}
