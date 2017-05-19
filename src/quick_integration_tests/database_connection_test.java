package quick_integration_tests;

import OzLympicGames.OzlGamesDAL.IOzlConfigRead;
import OzLympicGames.OzlGamesDAL.configFileMissingException;
import OzLympicGames.OzlGamesDAL.modelPackageConfig;
import OzLympicGames.GamesHelperFunctions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author dimz
 * @since 14/5/17.
 */
public class database_connection_test {

    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        Connection conn = null;
        Statement stmt;

        String connection = null;
        // build connection string
        try {
            connection = configReader.getConfigString("url", modelPackageConfig.MODEL_CONFIG_FILE);
        } catch (configFileMissingException err) {
            logger.fatal(err.getMessage());
            System.exit(0);
        }

        // make database connection work
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conn = DriverManager
                    .getConnection(connection, "master", "master");
            stmt = conn.createStatement();

            String sql = "INSERT INTO USER_TEST VALUES ('FOO3 BAR')";
            stmt.executeUpdate(sql);

        }  catch (SQLException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } finally{
            //finally block used to close resources
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main

}
