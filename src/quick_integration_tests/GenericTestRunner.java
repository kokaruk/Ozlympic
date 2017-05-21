package quick_integration_tests;

import javafx.scene.Parent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class GenericTestRunner {//extends Application {


    private static IGamesDAL gamesDAL;
    private static Logger logger = LogManager.getLogger();

    static {
        try {
            gamesDAL = GamesDAL.getInstance();
        } catch (Exception e) {
            logger.trace(e.getMessage());
            // do nothing
        }

    }

    private Stage primaryStage;
    private Parent rootLayout;
/*
    public static void main(String[] args) {
        getGames();
      //  launch(args);
    }

/*
    static void getGames(){
        try {
            List<OzlGame> gameMap = gamesDAL.getGameDAO().getGamesMap();
            logger.trace("Gameset: " + gameMap.size());
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }
*/

}
