package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.modelPackageConfig;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 * A Game Class
 */
public class OzlGame {
    // minimum / maximum gameParticipants in a game, sate from config file, populate at init
    private final int MIN_PARTICIPANTS;
    private final int MAX_PARTICIPANTS;

    // gameSportType of enum. public getter with lazy instantiation
    private final GameSports _gameSport;
    // game ID. Public only getter
    private final String _id;
    // is this a fresh game or replay
    private boolean _replay;
    // property with public setter for configuration reader.
    // Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader = OzlConfigRead.getInstance();
    // property array of game gameParticipants. participant at index 0 is always the official
    private Set<OzlParticipation> _participation = new HashSet<>();


    // Constructor
    OzlGame(String _id) {
        // Game ID, to be set by controller
        this._id = _id;
        MIN_PARTICIPANTS = configReader.getConfigInt("minParticipants", modelPackageConfig.MODEL_COFIG_FILE);
        MAX_PARTICIPANTS = configReader.getConfigInt("maxParticipants", modelPackageConfig.MODEL_COFIG_FILE);
        _gameSport = generateSport(_id);
    }

    // getters and setters

    public GameSports getGameSport() {
        return _gameSport;
    }

    public String getId() {
        return _id;
    }

    public boolean isReplay() {
        return _replay;
    }

    public void addParticipation(OzlParticipation aParticipation) {
        _participation.add(aParticipation);
    }

    public Set<OzlParticipation> getParticipation() {
        return _participation;
    }

    // Method to Generate GameSports enum based on ID string for constructor
    private GameSports generateSport(String gameId) {
        String sportsLetter = gameId.substring(0, 1).toLowerCase();

        return Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().orElse(null);
    }

    // assign new participant (be it athlete or referee)
    void addParticipant(GamesParticipant participant) throws OzlGameFullException {
        if (participant instanceof GamesAthlete){
            // determine if game not full
            if ((GamesHelperFunctions.athletesCount(this) + 1) > MAX_PARTICIPANTS) throw new OzlGameFullException(this);
            GamesHelperFunctions.createParticipation(participant, this);
        } else {
            // TODO add referee
        }
    }

    // method to make athletes to compete
    public void gamePlay() throws NotEnoughAthletesException, NoRefereeException {
        if (GamesHelperFunctions.athletesCount(this) <= MIN_PARTICIPANTS) throw new NotEnoughAthletesException(this);
        // TODO check if game has referee

        // make 'em compete
       for (OzlParticipation aParticipation : _participation) {
           if (aParticipation.getGamesParticipant() instanceof GamesAthlete) {
               // set time for game
               aParticipation.result = ((GamesAthlete) aParticipation.getGamesParticipant()).compete(aParticipation);
               // re-set score to 0
               aParticipation.score = 0;
           }
       }
       // set game as played
        _replay = true;
    }

    // TODO get official of the game to call the results

}

