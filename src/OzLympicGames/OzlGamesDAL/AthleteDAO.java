package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.GamesAthlete;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 14/5/17.
 */
class AthleteDAO implements IAthleteDAO {

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
    public GamesAthlete getNewAthlete(String name, Integer age, String state, String type) throws SQLException, ClassNotFoundException, IOException {
        String paramsVals = name + ',' + age.toString() + ',' + state + ',' + type;
        Integer idNum = ParticipantDAO.getNewIdNum(paramsVals);
        GamesAthlete athlete = new GamesAthlete(idNum, name, age, state, type);
        String ID = athlete.getId();
        ParticipantDAO.appendCSV(ID, paramsVals);

        return athlete;
    }
}
