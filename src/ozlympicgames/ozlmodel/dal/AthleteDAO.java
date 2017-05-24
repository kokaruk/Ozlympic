package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesAthlete;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dimz
 * @since 14/5/17.
 */
class AthleteDAO implements IAthleteDAO {

    private static String SQLContext = "PARTICIPANTS";
    private SQLPreBuilder preBuilder = new SQLPreBuilder(SQLContext);
    private static IGamesDAL gamesDAL;
    static {
        try {
            gamesDAL = GamesDAL.getInstance();
        } catch (Exception e) {
            // do nothing
        }
    }

    // singleton instance
    private static IAthleteDAO instance;

    // private constructor
    private AthleteDAO() {
    }

    // lazy instantiation
    public static IAthleteDAO getInstance() {
        if (instance == null) {
            instance = new AthleteDAO();
        }
        return instance;
    }


    @Override
    public GamesAthlete getNewAthlete(String name, Integer age, String state, String type)
            throws SQLException, ClassNotFoundException, IOException {
        String paramsVals = name + ',' + age.toString() + ',' + state + ',' + type;
        Integer idNum = preBuilder.getNewIdNum(paramsVals);
        GamesAthlete athlete = new GamesAthlete(idNum, name, age, state, type);
        String ID = athlete.getId();
        preBuilder.appendCSV(ID, paramsVals);
      //  gamesDAL.addAthlete(athlete);
        return athlete;
    }

    @Override
    public Map<String, GamesAthlete> getAthletesMap() throws SQLException, ClassNotFoundException, IOException {
        SQLPreBuilder AthleteLookupPrebuilder = new SQLPreBuilder("ATHLETES");
        CachedRowSet rs = AthleteLookupPrebuilder.getRowSetFromView("", "");
        Map<String, GamesAthlete> athletes = new HashMap<>();
        while (rs != null && rs.next()) {
            GamesAthlete athlete = new GamesAthlete(
                    rs.getInt("ID_NUM"),
                    rs.getString("NAME"),
                    rs.getInt("AGE"),
                    rs.getString("STATE"),
                    rs.getString("TYPE"),
                    rs.getInt("TOTALPOINTS"),
                    rs.getDouble("COMPETETIME"));
            athletes.put(athlete.getId(), athlete);
        }
        return athletes;
    }

    @Override
    public void updateAthlete(GamesAthlete athlete, String totalpoints)throws SQLException, ClassNotFoundException {
        SQLPreBuilder gameUpdatePreBuilder = new SQLPreBuilder("PARTICIPANTSUPDATE");
        Integer id =  Integer.parseInt(athlete.getId().substring(2));
        gameUpdatePreBuilder.updateRow( id.toString() ,
                "TOTALPOINTS", totalpoints);

    }

}
