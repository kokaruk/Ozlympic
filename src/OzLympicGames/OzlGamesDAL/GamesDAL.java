package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.GamesAthlete;
import OzLympicGames.OzlModel.GamesOfficial;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class GamesDAL implements IGamesDAL {

    private static IAthleteDAO athleteDAO;
    private static IRefereeDAO refereeDAO;
    static {
        athleteDAO = AthleteDAO.getInstance();
        refereeDAO = RefereeDAO.getInstance();
    }

    // singleton instance
    private static IGamesDAL instance;
    // private constructor
    private GamesDAL() {
    }
    // lazy instantiation
    public static IGamesDAL getInstance() {
        if (instance == null) {
            instance = new GamesDAL();
        }
        return instance;
    }

    @Override
    public GamesAthlete getNewAthlete(String name, Integer age, String state, String type)
            throws SQLException, ClassNotFoundException, IOException {
        return athleteDAO.getNewAthlete(name, age, state, type);
    }

    @Override
    public GamesOfficial getNewReferee(String name, Integer age, String state) throws SQLException, ClassNotFoundException, IOException {
        return refereeDAO.getNewReferee(name, age, state);
    }


}
