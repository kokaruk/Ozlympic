package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.List;
import java.util.Map;

/**
 * Created by dimi on 12/3/17.
 * All event calls through here
 */
public class OzlGamesController {

    final private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
    //list of my games
    private List<IOzlGame> myOzlGames;
    //list of all athletes and officials in all games
    private List<GamesAthlete> mYgamesAthletes;
    // counter for athleteTypes
    private Map<AthleteType, Integer> sportsCounterMap;

    public OzlGamesController() {
        //init previous games
        myOzlGames = ormDataReader.getGames();
        //init all games participants
        mYgamesAthletes = ormDataReader.getMyGamesAthletes();
        //athletetypes counter init
        sportsCounterMap = ormDataReader.getSportsCounterMap();
    }

}
