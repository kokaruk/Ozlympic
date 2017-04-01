package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlModel.AthleteType;
import OzLympicGames.OzlympicGamesMVC.OzlModel.GamesAthlete;
import OzLympicGames.OzlympicGamesMVC.OzlModel.IGamesParticipant;
import OzLympicGames.OzlympicGamesMVC.OzlModel.IOzlGame;

import java.util.List;
import java.util.Map;

/**
 * Created by dimi on 22/3/17.
 * ORM Mapping Interface
 */
interface IOzlGamesORM {
    // get all athletes
    List<GamesAthlete> getMyGamesAthletes();

    // get all games
    List<IOzlGame> getGames();

    // method to generate game official
    IGamesParticipant getGameOfficial(String participantId);

    // method to generate game Athlete without ID
    IGamesParticipant getGameAthlete();

    // sports type counter
    Map<AthleteType, Integer> getSportsCounterMap();

}
