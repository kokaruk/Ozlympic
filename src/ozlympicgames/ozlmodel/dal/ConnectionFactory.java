package ozlympicgames.ozlmodel.dal;

import ozlympicgames.ozlmodel.GamesHelperFunctions;
import com.sun.rowset.CachedRowSetImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.CachedRowSet;
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
        Integer id;

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

    static CachedRowSet getRowSet(String tableName, String selectColumns,
                                String whereColumns, String paramsValues ) throws SQLException, ClassNotFoundException {
        String sql =
                "SELECT " + selectColumns + " FROM " + tableName + whereString(whereColumns);
        ResultSet rs;
        CachedRowSetImpl crs = new CachedRowSetImpl();
        try(Connection con  = getConnection()){
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                fillParameters(ps, paramsValues);
                rs = ps.executeQuery();
                crs.populate(rs);
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        }
        return crs;
    }

    static void updateRow(String tableName, String updateColumns, String updateValues,
                          String whereColumns, String paramsValues ) throws SQLException, ClassNotFoundException {
        String sql =
                "UPDATE " + tableName + " SET " + setColumnWildCardBuilder(whereColumns) + " WHERE " + setValues(updateColumns, updateValues) ;
        try(Connection con  = getConnection()){
            con.setAutoCommit(false);
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                fillParameters(ps, paramsValues);
                ps.executeUpdate();
            } catch (SQLException e) {
                logger.error(e.getMessage());
                con.rollback();
                con.setAutoCommit(true);
                throw e;
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            throw e;
        }
    }


    private static String whereString(String whereColumns) {
          return ( whereColumns.equals("") ) ? "" : " WHERE  " + selectColumnWildCardBuilder(whereColumns);
    }


    private static void fillParameters(PreparedStatement ps, String paramsValues ) {
        if(!paramsValues.isEmpty()) {
            String[] paramValuesArray = paramsValues.split(REGEX_SPLIT_OPTION);
            int i = 0;
            do {
                try {
                    ps.setString(i + 1, paramValuesArray[i]);
                } catch (SQLException e) {
                    logger.error(e.getMessage());
                }
                i++;
            } while (i < paramValuesArray.length);
        }
    }

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.hsqldb.jdbcDriver");
      return DriverManager
              .getConnection(url, "master", "master");
    }

    private static String setValues(String columns, String values) {
        String[] columnsArray = columns.split(REGEX_SPLIT_OPTION);
        String[] valuesArray = values.split(REGEX_SPLIT_OPTION);
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < columnsArray.length; i++){
            c.append(i==0 ? columnsArray[i] + "=" + valuesArray[i] : " AND " + columnsArray[i] + "=" + valuesArray[i]);
        }
        return c.toString();
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

    private static String setColumnWildCardBuilder(String columns){
        String[] columnsArray = columns.split(REGEX_SPLIT_OPTION);
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < columnsArray.length; i++){
            c.append(i==0 ? columnsArray[i] + "=? " : ",  " + columnsArray[i] + "=? ");
        }
        return c.toString();
    }


}
