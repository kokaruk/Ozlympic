package OzLympicGames.OzlGamesDAL;

import OzLympicGames.OzlModel.AthleteType;
import OzLympicGames.OzlModel.GamesAthlete;

/**
 * Data access layer of Athlete class.
 * @author dimz
 * @since 11//05/17
 */
public interface IAthleteDAO {
    GamesAthlete getNewAthlete(String name, Integer age, String state, AthleteType type) throws Exception;
}
