package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.GamesOfficial;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class RefereeDAO implements IRefereeDAO {

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
        Integer idNum = ParticipantDAO.getNewIdNum(paramsVals);
        GamesOfficial referee = new GamesOfficial(idNum, name, age, state);
        String ID = referee.getId();
        ParticipantDAO.appendCSV(ID, paramsVals);

        return referee;
    }

}
