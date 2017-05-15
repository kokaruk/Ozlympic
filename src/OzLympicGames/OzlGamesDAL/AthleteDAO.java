package OzLympicGames.OzlGamesDAL;

import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlModel.AthleteType;
import OzLympicGames.OzlModel.GamesAthlete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dimz
 * @since 14/5/17.
 */
public class AthleteDAO implements IAthleteDAO {

    private static Logger logger = LogManager.getLogger();
    static final String TABLE_NAME = "PARTICIPANTS";
    static final String VIEW_NAME = "PARTICIPANTS_VIEW";
    static final String COLUMN_NAMES = "NAME,AGE,STATE,TYPE";

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
    public GamesAthlete getNewAthlete(String name, Integer age, String state, AthleteType type) throws Exception {

        String[] paramsVals = new String[]{name, age.toString(), state, type.name()};
        String wildcards = GamesHelperFunctions.buildWildCards(paramsVals.length);
        ResultSet rs = null;
        String ID = "ID";
        String idValue = null;

        try{
            ConnectionFactory.insertStatement(TABLE_NAME, COLUMN_NAMES, wildcards, paramsVals);
            rs = ConnectionFactory.getData(VIEW_NAME, ID, COLUMN_NAMES, paramsVals);
            rs.next();
            ID = rs.getString("ID");
            appendCSV(ID, paramsVals);
        } catch(Exception se){
            logger.error(se.getMessage());
        } finally {
            ConnectionFactory.closeConnection(null, null, rs);
        }
        return new GamesAthlete(ID, name, age, state, type);
    }

    private void appendCSV(String ID, String[] paramsVals) throws IOException {
        List<String> athleteData = new ArrayList<>();
        athleteData.add(ID);
        athleteData.addAll(Arrays.asList(paramsVals));
        FileWriter writer = null;
        try {
            writer = new FileWriter("output.csv");
            CSVUtils.writeLine(writer, athleteData);
        } catch (IOException e) {
            System.err.println("File cannot be created, or cannot be opened");
            logger.warn( e.getMessage());
            System.exit(0);
        } finally {
            writer.close();
        }
    }


}
