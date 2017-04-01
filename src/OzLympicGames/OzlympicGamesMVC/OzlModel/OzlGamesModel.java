package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.List;
import java.util.Map;

/**
 * Created by dimi on 12/3/17.
 * All events calls through here
 */
public class OzlGamesModel {

    private List<IOzlGame> myOzlGames;
    private List<GamesAthlete> mYgamesAthletes;
    private Map<AthleteType, Integer> sportsCounterMap;

    public List<IOzlGame> getMyOzlGames() {
        return myOzlGames;
    }

    public void setMyOzlGames(List<IOzlGame> myOzlGames) {
        this.myOzlGames = myOzlGames;
    }

    public List<GamesAthlete> getmYgamesAthletes() {
        return mYgamesAthletes;
    }

    public void setmYgamesAthletes(List<GamesAthlete> mYgamesAthletes) {
        this.mYgamesAthletes = mYgamesAthletes;
    }

    public Map<AthleteType, Integer> getSportsCounterMap() {
        return sportsCounterMap;
    }

    public void setSportsCounterMap(Map<AthleteType, Integer> sportsCounterMap) {
        this.sportsCounterMap = sportsCounterMap;
    }

    public OzlGamesModel() {
        //init previous games
        IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
        myOzlGames = ormDataReader.getGames();
        //init all games participants
        mYgamesAthletes = ormDataReader.getMyGamesAthletes();
        //athlete types counter init
        sportsCounterMap = ormDataReader.getSportsCounterMap();
    }

}
