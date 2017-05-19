package OzLympicGames.OzlGamesDAL;

import OzLympicGames.GamesHelperFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
/**
 * @author dimz
 * @since 14/5/17.
 */

public class ConnectionFactory{

    private ConnectionFactory(){}
    private static String REGEX_SPLIT_OPTION;
    private static Logger logger = LogManager.getLogger();
    private static IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
    private static String url;
    // setup
    static{
        try{
            url = configReader.getConfigString("url", modelPackageConfig.MODEL_CONFIG_FILE);
            REGEX_SPLIT_OPTION = configReader.getConfigString("REGEX_SPLIT_OPTION", modelPackageConfig.MODEL_CONFIG_FILE);
        }catch(Exception e){
            logger.fatal("Missing config file " + modelPackageConfig.MODEL_CONFIG_FILE);
            System.exit(1);
        }
    }

    static int insertStatement(String tableName, String columns, String paramsValues ) throws SQLException, ClassNotFoundException {
        String wildcards = ConnectionFactory.buildWildCards(paramsValues);
        String sql =
                "INSERT INTO " + tableName + "(" + columns + ")" +
                " VALUES (" + wildcards + ")";
        Integer id = 0;

        try(Connection con  = getConnection()){
            con.setAutoCommit(false);
            try(PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                fillParameters(ps, paramsValues  );
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                id = rs != null && rs.next() ? rs.getInt(1) : 0;
            } catch (SQLException e) {
                logger.error(e.getMessage());
                con.rollback();
                con.setAutoCommit(true);
                throw e;
            }
            con.commit();
            con.setAutoCommit(true);
        }
        return id;
    }

    static ResultSet getData(String tableName, String selectColumns,
                             String whereColumns, String paramsValues ) throws Exception {
        String sql =
                "SELECT " + selectColumns + " FROM " + tableName +
                        " WHERE  " + selectColumnWildCardBuilder(whereColumns);
        ResultSet rs = null;

        try(Connection con  = getConnection()){
            con.setAutoCommit(false);
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                fillParameters(ps, paramsValues  );
                rs = ps.executeQuery();
                rs.next();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                con.rollback();
                con.setAutoCommit(true);
            }
            con.commit();
            con.setAutoCommit(true);
        } catch(Exception e){
            logger.error(e.getMessage());
        }
        return rs;
    }


    private static void fillParameters(PreparedStatement ps, String paramsValues ) {
        String[] paramValuesArray = paramsValues.split(REGEX_SPLIT_OPTION);
        for (int i = 0; i < paramValuesArray.length; i++) {
            try {
                ps.setString(i+1, paramValuesArray[i]);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
      return DriverManager
              .getConnection(url, "master", "master");
    }

    /**
     * Build string of ? for sql, list of wildcards
     * @param paramsValues string of parameters
     * @return string of comma separated '?'
     */
    private static String buildWildCards(String paramsValues){
        int wildCards = paramsValues.split(REGEX_SPLIT_OPTION).length;
        StringBuilder w = new StringBuilder();
        for (int i = 0; i<wildCards; i++){
            w.append( i == 0 ? "?" : ",?");
        }
        return w.toString();
    }

    /**
     * Build a list of pairs of COLUMN=WILDCARD for prepared statement
     * @param columns comma separated strings of column names
     * @return coma separated pairs
     */
    private static String selectColumnWildCardBuilder(String columns){
        String[] columnsArray = columns.split(REGEX_SPLIT_OPTION);
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < columnsArray.length; i++){
            c.append(i==0 ? columnsArray[i] + "=? " : "AND " + columnsArray[i] + "=? ");
        }
        return c.toString();
    }


}
