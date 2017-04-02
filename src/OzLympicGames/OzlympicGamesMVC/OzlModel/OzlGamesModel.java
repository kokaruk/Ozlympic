package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by dimi on 12/3/17.
 * All events calls through here
 */
public class OzlGamesModel {

    private final List<IOzlGame> myOzlGames;
    private List<GamesAthlete> myGamesAthletes;
    @SuppressWarnings("unused") //currently unused, might come back in the future
    private Map<AthleteType, Integer> sportsCounterMap;
    private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();

    public OzlGamesModel() {
        //init previous games
        ormDataReader = OzlGamesORMFake.getInstance();
        myOzlGames = ormDataReader.getGames();
        //init all games participants
        myGamesAthletes = ormDataReader.getMyGamesAthletes();
        //athlete types counter init
        sportsCounterMap = ormDataReader.getSportsCounterMap();
    }

    public List<IOzlGame> getMyOzlGames() {
        return myOzlGames;
    }

    public List<GamesAthlete> getMyGamesAthletes() {
        return myGamesAthletes;
    }

    public OzlGame newGameWithSport(GameSports mySport) {
        int gameSportCounter = Math.toIntExact(
                myOzlGames.stream().filter(Objects::nonNull).filter(game -> ((OzlGame) game).getGameSportType().equals(mySport)).count()
        );
        OzlGame myOzlGame = new OzlGame(String.format("%s%03d", Character.toUpperCase(mySport.name().charAt(0)), gameSportCounter));
        myOzlGame.setGameParticipants(ormDataReader.getOfficialAndAthleteArray(myOzlGame));
        // update data fields: athletes & counter list
        myGamesAthletes = ormDataReader.getMyGamesAthletes();
        sportsCounterMap = ormDataReader.getSportsCounterMap();
        myOzlGames.add(myOzlGame);

        return myOzlGame;
    }

}
