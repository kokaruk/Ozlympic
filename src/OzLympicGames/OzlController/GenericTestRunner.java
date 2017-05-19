package OzLympicGames.OzlController;

import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlGamesDAL.GamesDAL;
import OzLympicGames.OzlGamesDAL.IGamesDAL;
import OzLympicGames.OzlModel.GamesAthlete;
import OzLympicGames.OzlModel.GamesOfficial;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class GenericTestRunner {

    private static IGamesDAL gamesDAL = GamesDAL.getInstance();
    private static Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        addParticipant();
    }


    static void addParticipant(){

        GamesAthlete athl;
        GamesOfficial ref;
        String name = GamesHelperFunctions.getRandomName();
        int age = GamesHelperFunctions.getRandomAge();
        String state = GamesHelperFunctions.getRandomState();
        String type = GamesHelperFunctions.getRandomAthleteType().name();
        try {
            athl = gamesDAL.getNewAthlete(name, age, state, type);
            logger.trace("New Athl OK " + athl.getId());
            ref = gamesDAL.getNewReferee(name,age,state);
            logger.trace("New Ref OK " + ref.getId());
        } catch (Exception e) {
            logger.fatal(e.getMessage());
        }
    }
}
