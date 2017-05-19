package OzLympicGames.OzlGamesDAL;

import OzLympicGames.GamesHelperFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dimz
 * @since 19/5/17.
 */
final class ParticipantDAO {
    private static final String PARTICIPANTS_CSV = "participants.csv.txt";
    private static final String BD_ERR_ID = "DbError";
    private static final String TABLE_NAME = "PARTICIPANTS";
    private static final String VIEW_NAME = "PARTICIPANTS_VIEW";
    private static final String COLUMN_NAMES = "NAME,AGE,STATE,TYPE";
    private static String REGEX_SPLIT_OPTION;
    private static Logger logger = LogManager.getLogger();
    static {
        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        try {
            REGEX_SPLIT_OPTION = configReader.getConfigString("REGEX_SPLIT_OPTION", modelPackageConfig.MODEL_CONFIG_FILE);;
        } catch (configFileMissingException e) {
            logger.fatal(e.getMessage());
            System.exit(1);
        }
    }

    private ParticipantDAO(){}


    static Integer getNewIdNum(String paramsVals) throws SQLException, ClassNotFoundException, IOException {
        String ID = "ID";
        SQLException error = null;
        try {
            Integer idNum = ConnectionFactory.insertStatement(TABLE_NAME, COLUMN_NAMES, paramsVals); // populate the database
            // database connection working
            if (CSVUtils.findBrokenID(PARTICIPANTS_CSV, BD_ERR_ID)) {
                logger.debug("got broken entries in " + PARTICIPANTS_CSV + " attempting to repopulate");
                CSVUtils.fixBrokenID(PARTICIPANTS_CSV, BD_ERR_ID, TABLE_NAME, COLUMN_NAMES);
            }
            return idNum;
        } catch (SQLException e) {
            ID = BD_ERR_ID;
            error = e;
            logger.error("can't write new participant to database. Will throw");
        } catch (IOException e) {
            logger.warn("fail to assign ID in CSV records");
        } finally {
            // write to CSV regardless or skip if constraint violation
            if (error != null) {
                if (error.getErrorCode() != -104) appendCSV(ID, paramsVals);
                throw error;
            }
        }
        // if not returned athlete before, return null
        return null;
    }


    static void appendCSV(String ID, String paramsVals) {
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
