package OzLympicGames.OzlympicGamesMVC.OzlModel;

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

    // array of official + athletes
    GamesParticipant[] getOfficialAndAthleteArray(OzlGame myOzlGame);

    // randomly generates a player basen on game-sports needs
    GamesParticipant getMyNewAthlete(OzlGame myOzlGame);


}
