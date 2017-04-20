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
    private Map<AthleteType, Integer> sportsAthletesCounterMap;
    private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
    // current active game
    private OzlGame currentActiveGame;

    public OzlGamesModel() {
        //init previous games
        ormDataReader = OzlGamesORMFake.getInstance();
        myOzlGames = ormDataReader.getGames();
        //init all games participants
        myGamesAthletes = ormDataReader.getMyGamesAthletes();
        //athlete types counter init
        sportsAthletesCounterMap = ormDataReader.getSportsCounterMap();
    }

    public OzlGame getCurrentActiveGame() {
        return currentActiveGame;
    }

    public void setCurrentActiveGame(OzlGame currentActiveGame) {
        this.currentActiveGame = currentActiveGame;
    }

    public List<IOzlGame> getMyOzlGames() {
        return myOzlGames;
    }

    public List<GamesAthlete> getMyGamesAthletes() {
        return myGamesAthletes;
    }

    // creates new game based on sport type selection
    public void newGameWithSport(GameSports mySport) {
        int gameSportCounter = Math.toIntExact(
                myOzlGames.stream().filter(Objects::nonNull).filter(game -> ((OzlGame) game).getGameSportType().equals(mySport)).count()
        );
        OzlGame newGame = new OzlGame(String.format("%s%03d", Character.toUpperCase(mySport.name().charAt(0)), ++gameSportCounter));
        currentActiveGame = newGame;
        myOzlGames.add(newGame);

        int gameParticipantsBounds = newGame.getGameParticipants().length;
        GamesParticipant[] myParticipants = new GamesParticipant[gameParticipantsBounds];
        // add new official to game and vice<>versa
        myParticipants[0] = (GamesParticipant) ormDataReader.getGameOfficial(String.format("REF%03d", myOzlGames.size()));
        myParticipants[0].setMyOzlGame(newGame);
        newGame.setGameParticipants(myParticipants);
    }

    public void autoSetupParticipantsNewGame() {
        currentActiveGame.setGameParticipants(ormDataReader.getOfficialAndAthleteArray(currentActiveGame));
        myGamesAthletes = ormDataReader.getMyGamesAthletes();
        sportsAthletesCounterMap = ormDataReader.getSportsCounterMap();
    }

    public void autoSetupAthlete() {
        GamesParticipant newAthlete = ormDataReader.getMyNewAthlete(currentActiveGame);
        currentActiveGame.setAthlete(newAthlete);
        newAthlete.setMyOzlGame(currentActiveGame);
        myGamesAthletes = ormDataReader.getMyGamesAthletes();
        sportsAthletesCounterMap = ormDataReader.getSportsCounterMap();
    }

}
