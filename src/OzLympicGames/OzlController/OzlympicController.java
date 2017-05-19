package OzLympicGames.OzlController;

import OzLympicGames.OzlGamesDAL.GamesDAL;
import OzLympicGames.OzlGamesDAL.IGamesDAL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main games controller class
 * @author dimz
 * @since 11/5/17
 */
public class OzlympicController {

    private static Logger logger = LogManager.getLogger();
    private static IGamesDAL gamesDAL = GamesDAL.getInstance();



}
