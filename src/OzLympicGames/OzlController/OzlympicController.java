package OzLympicGames.OzlController;

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
        String name = "Foo Bar 2";
        int age = 23;
        String state = "Victoria";
        AthleteType type = AthleteType.cyclist;
        try {
            athl = athleteDAO.getNewAthlete(name, age, state, type);
            logger.trace("New Athl OK");
        } catch (Exception e) {
           logger.fatal(e.getMessage());
        }
    }

}
