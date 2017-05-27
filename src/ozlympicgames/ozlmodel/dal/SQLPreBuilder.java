package ozlympicgames.ozlmodel.dal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.GamesHelperFunctions;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author dimz
 * @since 19/5/17.
 */
final class SQLPreBuilder {
    private final String CSV_PATH;
    private final String BD_ERR_ID;
    private final String TABLE_NAME;
    private final String VIEW_NAME;
    private final String COLUMN_NAMES;
    private final String REGEX_SPLIT_OPTION;
    private Logger logger = LogManager.getLogger();

    SQLPreBuilder(String sqlContext) {
        Properties props = new Properties();
        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        try {
            props = configReader.getProperties(modelPackageConfig.MODEL_CONFIG_FILE);
        } catch (IOException e) {
            logger.fatal(e.getMessage());
            System.exit(1);
        }
        REGEX_SPLIT_OPTION = props.getProperty("REGEX_SPLIT_OPTION");
        BD_ERR_ID = props.getProperty("BD_ERR_ID");
        TABLE_NAME = props.getProperty(sqlContext + "_TABLE_NAME");
        VIEW_NAME = props.getProperty(sqlContext + "_VIEW_NAME");
        COLUMN_NAMES = props.getProperty(sqlContext + "_COLUMN_NAMES");
        CSV_PATH = props.getProperty(sqlContext + "_CSV");
    }

    Integer getNewIdNum(String paramsVals) throws SQLException, ClassNotFoundException {
        String ID = "ID";
        SQLException error = null;
        try {
            Integer idNum = ConnectionFactory.insertStatement(TABLE_NAME, COLUMN_NAMES, paramsVals); // populate the database
            // database connection working
            if (CSVUtils.findBrokenID(CSV_PATH, BD_ERR_ID)) {
                logger.debug("got broken entries in " + CSV_PATH + " attempting to repopulate");
                CSVUtils.fixBrokenID(CSV_PATH, BD_ERR_ID, TABLE_NAME, COLUMN_NAMES);
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

    CachedRowSet getRowSet(String whereColumns, String paramsValues) throws SQLException, ClassNotFoundException {
        return ConnectionFactory.getRowSet(TABLE_NAME, "*", whereColumns, paramsValues);
    }

    CachedRowSet getRowSetFromView(String whereColumns, String paramsValues) throws SQLException, ClassNotFoundException {
        return ConnectionFactory.getRowSet(VIEW_NAME, "*", whereColumns, paramsValues);
    }

    void updateRow(String updateValues, String whereColumns, String paramsValues) throws SQLException, ClassNotFoundException {
        ConnectionFactory.updateRow(TABLE_NAME, COLUMN_NAMES, updateValues, whereColumns, paramsValues);
    }

    void appendCSV(String ID, String paramsVals) {
        List<String> data = new ArrayList<>();
        data.add(ID);
        data.addAll(Arrays.asList(paramsVals.split(REGEX_SPLIT_OPTION)));
        try {
            CSVUtils.writeLine(data, CSV_PATH);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
