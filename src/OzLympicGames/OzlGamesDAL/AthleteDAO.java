package OzLympicGames.OzlGamesDAL;

import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlModel.AthleteType;
import OzLympicGames.OzlModel.GamesAthlete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dimz
 * @since 14/5/17.
 */
public class AthleteDAO implements IAthleteDAO {

    private static final String PARTICIPANTS_CSV = "participants.csv.txt";
    private static Logger logger = LogManager.getLogger();
    private static final String BD_ERR_ID = "DbError";
    private static final String TABLE_NAME = "PARTICIPANTS";
    private static final String VIEW_NAME = "PARTICIPANTS_VIEW";
    private static final String COLUMN_NAMES = "NAME,AGE,STATE,TYPE";
    private static String REGEX_SPLIT_OPTION;
    private static IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
    static {
        try {
            REGEX_SPLIT_OPTION = configReader.getConfigString("REGEX_SPLIT_OPTION", modelPackageConfig.MODEL_CONFIG_FILE);;
        } catch (configFileMissingException e) {
            logger.fatal(e.getMessage());
            System.exit(1);
        }
    }

    // singleton instance
    private static AthleteDAO instance;
    // private constructor
    private AthleteDAO() {
    }
    // lazy instantiation
    public static AthleteDAO getInstance() {
        if (instance == null) {
            instance = new AthleteDAO();
        }
        return instance;
    }


    @Override
    public GamesAthlete getNewAthlete(String name, Integer age, String state, AthleteType type) throws SQLException, ClassNotFoundException {

        String paramsVals = name + ',' + age.toString() + ',' + state + ',' + type.name();
        String ID = "ID";
        Integer idNum = 0;
        SQLException error = null;
        try {
            idNum = ConnectionFactory.insertStatement(TABLE_NAME, COLUMN_NAMES, paramsVals); // populate the database
            // database connection working
            ID = GamesAthlete.idPrefix(type) + String.format("S%04d", idNum);
            if (CSVUtils.findBrokenID(PARTICIPANTS_CSV, BD_ERR_ID)) {
                logger.debug("got broken entries");
                CSVUtils.fixBrokenID(PARTICIPANTS_CSV, BD_ERR_ID, TABLE_NAME, COLUMN_NAMES);
            }
        } catch (SQLException e) {
            ID = BD_ERR_ID;
            error = e;
            logger.error("can't write new athlete to database. Will throw");
        } catch (IOException e) {
            logger.warn("fail to assign ID in CSV records");
        } finally {
            // write to CSV regardless
            appendCSV(ID, paramsVals);
            if (error != null) throw error;
        }
        /*
         update global counter
         change of mind. sequence numbers should be controlled by database.
         all new data should then
         IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
         configReader.setConfigString( "PARTICIPANTS_COUNTER", idNum.toString(),modelPackageConfig.COUNTERS_COUNFIG_FILE);
         */
        return new GamesAthlete(ID, name, age, state, type);
}

    private void appendCSV(String ID, String paramsVals) {
        List<String> athleteData = new ArrayList<>();
        athleteData.add(ID);
        athleteData.addAll(Arrays.asList(paramsVals.split(REGEX_SPLIT_OPTION)));
        try {
            CSVUtils.writeLine(athleteData, PARTICIPANTS_CSV);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }


}
