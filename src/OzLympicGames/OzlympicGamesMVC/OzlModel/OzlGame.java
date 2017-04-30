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
    private boolean gamePlayed;
    // property with public setter for configuration reader.
    // Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader = OzlConfigRead.getInstance();
    // property array of game gameParticipants
    private Set<OzlParticipation> _participation = new HashSet<>();
    private GamesOfficial _referee;


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
        return gamePlayed;
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
    void addParticipant(GamesParticipant participant) throws OzlGameFullException, WrongSportException {
        if (participant instanceof GamesAthlete) {
            // if wrong type of sports assignment, throw error
            if (((GamesAthlete) participant).getAthleteType().getSport().size() == 1
                    && ((GamesAthlete) participant).getAthleteType().getSport().iterator().next() != this.getGameSport()) {
                throw new WrongSportException(participant, this);
            }
            // determine if game not full
            if ((athletesCount() + 1) > MAX_PARTICIPANTS) throw new OzlGameFullException(this);
            //add new participation to participation set
            _participation.add(new OzlParticipation((GamesAthlete)participant, this));

        } else {
            // add referee, or (if already exists, overwrite)
            // if referee exists, remove reference from this game
            //  remove game from referee if game exist
            removeParticipant(participant);
            _referee = (GamesOfficial) participant;
            ((GamesOfficial) participant).setGame(this);
        }
    }

    // add method to remove an athlete or participant
    void removeParticipant(GamesParticipant participant) {
        if (participant instanceof GamesAthlete) {

        } else {
            if (_referee != null && _referee.equals(participant)) { // if referee already exists and not the same
                _referee.removeGame();
            }
        }

    }


    // method to make athletes to compete
    public void gamePlay() throws NotEnoughAthletesException, NoRefereeException {
        if (athletesCount() <= MIN_PARTICIPANTS) throw new NotEnoughAthletesException(this);
        // check if game has referee
        if (this._referee == null) throw new NoRefereeException(this);
        // make 'em compete
        for (OzlParticipation aParticipation : _participation) {
            // if game is being replayed
            if (gamePlayed && aParticipation.score > 0){
                // reduce total points of an athlete by previous result
                aParticipation.gamesAthlete.setTotalPoints( aParticipation.score * -1 );
                // re-set score to 0
                aParticipation.score = 0;
            }

            // set time for game
            aParticipation.result = (aParticipation.getGamesAthlete()).compete(aParticipation);
        }
        // set game as played
        gamePlayed = true;
        // ask official to award points
        _referee.awardPoints(this);
    }

    // get three winners sorted by time
    List<GamesAthlete> getGameAthletesWinnersSortedByTime() throws GameNeverPlayedException {
        if (!gamePlayed) throw new GameNeverPlayedException(this);
        List<GamesAthlete> gameAthletesWinners = new ArrayList<>();
        if (athletesCount() > 0) {
            Comparator<OzlParticipation> byLastGameTime = Comparator
                    .comparingDouble(OzlParticipation::getResult)
                    .thenComparingDouble(OzlParticipation::getResult);

            gameAthletesWinners = _participation.stream()
                    .filter(Objects::nonNull)
                    .sorted(byLastGameTime)
                    .map(OzlParticipation::getGamesAthlete)
                    .filter(GamesAthlete.class::isInstance)
                    .map(GamesAthlete.class::cast)
                    .limit(3)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return gameAthletesWinners;
    }

    // get all athletes as list from participation object
    public List<GamesAthlete> getGameAthletes(OzlGame game) {
        List<GamesAthlete> gameAthletes = game.getParticipation().stream()
                .filter(Objects::nonNull)
                .map(OzlParticipation::getGamesAthlete)
                .filter(GamesAthlete.class::isInstance)
                .map(GamesAthlete.class::cast)
                .collect(Collectors.toCollection(ArrayList::new));

        return gameAthletes;
    }

    // count total athletes for a game
    Integer athletesCount() {
        return Math.toIntExact(_participation.stream()
                .filter(Objects::nonNull)
                .map(OzlParticipation::getGamesAthlete)
                .filter(GamesAthlete.class::isInstance)
                .count()
        );
    }

}

