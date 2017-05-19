package OzLympicGames.OzlController;

import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlGamesDAL.AthleteDAO;
import OzLympicGames.OzlGamesDAL.IAthleteDAO;
import OzLympicGames.OzlModel.AthleteType;
import OzLympicGames.OzlModel.GamesAthlete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main games controller class
 * @author dimz
 * @since 11/5/17
 */
public class OzlympicController {

    private static Logger logger = LogManager.getLogger();

    public void addAthlete(){
        IAthleteDAO athleteDAO = AthleteDAO.getInstance();

        GamesAthlete athl;
        String name = GamesHelperFunctions.getRandomName();
        int age = GamesHelperFunctions.getRandomAge();
        String state = GamesHelperFunctions.getRandomState();
        AthleteType type = GamesHelperFunctions.getRandomAthleteType();
        try {
            athl = athleteDAO.getNewAthlete(name, age, state, type);
            logger.trace("New Athl OK");
        } catch (Exception e) {
           logger.fatal(e.getMessage());
        }
    }

}
