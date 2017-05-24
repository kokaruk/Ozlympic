package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesOfficial;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class RefereeDAO implements IRefereeDAO {

    private static String SQLContext = "PARTICIPANTS";
    private SQLPreBuilder preBuilder = new SQLPreBuilder(SQLContext);

    // singleton instance
    private static IRefereeDAO instance;

    // private constructor
    private RefereeDAO() {
    }

    // lazy instantiation
    public static IRefereeDAO getInstance() {
        if (instance == null) {
            instance = new RefereeDAO();
        }
        return instance;
    }


    @Override
    public GamesOfficial getNewReferee(String name, Integer age, String state) throws SQLException, ClassNotFoundException, IOException {
        String paramsVals = name + ',' + age.toString() + ',' + state + ',' + "referee";
        Integer idNum = preBuilder.getNewIdNum(paramsVals);
        GamesOfficial referee = new GamesOfficial(idNum, name, age, state);
        String ID = referee.getId();
        preBuilder.appendCSV(ID, paramsVals);
        return referee;
    }

    @Override
    public Map<String, GamesOfficial> getOfficialsMap() throws SQLException, ClassNotFoundException, IOException {
        CachedRowSet rs = preBuilder.getRowSet("TYPE", "referee");
        Map<String, GamesOfficial> officials = new HashMap<>();
        while (rs != null && rs.next()) {
            GamesOfficial referee = new GamesOfficial(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("AGE"), rs.getString("STATE"));
            officials.put(referee.getId(), referee);
        }
        return officials;
    }
}
