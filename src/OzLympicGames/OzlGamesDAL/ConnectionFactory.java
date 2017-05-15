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
    private static Logger logger = LogManager.getLogger();
    private static IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
    private static String url;
    // setup
    static{
        try{
            url = configReader.getConfigString("url", modelPackageConfig.MODEL_CONFIG_FILE);
        }catch(Exception e){
            logger.fatal("Missing config file " + modelPackageConfig.MODEL_CONFIG_FILE);
        }
    }

    static int insertStatement(String tableName, String columns, String wildcards, String[] paramsValues ) throws Exception {
        String sql =
                "INSERT INTO " + tableName + "(" + columns + ")" +
                " VALUES (" + wildcards + ")";
        Integer res = 0;
        try(Connection con  = getConnection()){
            con.setAutoCommit(false);
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                fillParameters(ps, paramsValues  );
                res = ps.executeUpdate();
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
        return res;
    }

    static ResultSet getData(String tableName, String selectColumns,
                             String whereColumns, String[] paramsValues ) throws Exception {
        String sql =
                "SELECT " + selectColumns + " FROM " + tableName +
                        " WHERE  " + GamesHelperFunctions.selectColumnWildCardBuilder(whereColumns);
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


    private static void fillParameters(PreparedStatement ps, String[] paramsValues ) {
        for (int i = 0; i < paramsValues.length; i++) {
            try {
                ps.setString(i+1, paramsValues[i]);
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static Connection getConnection() throws Exception{
        Class.forName("org.hsqldb.jdbcDriver");
      return DriverManager
              .getConnection(url, "master", "master");
    }

    static void closeConnection(Connection con, PreparedStatement st, ResultSet rs) throws Exception{
        if(con!=null)
            con.close();
        if(st!=null)
            st.close();
        if(rs!=null)
            rs.close();
    }
}
