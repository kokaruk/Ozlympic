package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.modelPackageConfig;

import java.util.*;
import java.util.function.Predicate;
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
    // property array of game gameParticipants
    private Set<OzlParticipation> _participation = new HashSet<>();
    private GamesOfficial _referee;

    // Constructor
    OzlGame(String _id) {
        // Game ID, to be set by controller
        this._id = _id;
        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        MIN_PARTICIPANTS = configReader.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_COFIG_FILE);
        MAX_PARTICIPANTS = configReader.getConfigInt("MAX_PARTICIPANTS", modelPackageConfig.MODEL_COFIG_FILE);
        _gameSport = generateSport(_id);
    }

    GamesOfficial get_referee() {
        return _referee;
    }

    // getters and setters

    public GameSports getGameSport() {
        return _gameSport;
    }

    public String getId() {
        return _id;
    }

    public boolean isGamePlayed() {
        return gamePlayed;
    }

    public Set<OzlParticipation> getParticipation() {
        return _participation;
    }

    // Method to Generate GameSports enum based on ID string for constructor
    private GameSports generateSport(String gameId) throws NoSuchElementException {
        String sportsLetter = gameId.substring(0, 2).toLowerCase();

        return Arrays.stream(GameSports.values())
                .filter(x -> x.name().startsWith(sportsLetter))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    // assign new participant (be it athlete or referee)
    void addParticipant(GamesParticipant participant) throws OzlGameFullException, WrongSportException {
        // ... and check if athlete already in the game, and do nothing if already here
        if (participant instanceof GamesAthlete && !getGameAthletes().contains(participant)) {
            // if wrong type of sports assignment, throw error
            if (((GamesAthlete) participant).getAthleteType().getSport().size() == 1
                    && ((GamesAthlete) participant).getAthleteType().getSport().iterator().next() != this.getGameSport()) {
                throw new WrongSportException(participant, this);
            }
            // determine if game not full
            if ((athletesCount() + 1) > MAX_PARTICIPANTS) throw new OzlGameFullException(this);
            // adding more athletes means game never played with new participants
            if (gamePlayed) gamePlayed = false;
            // create new participation
            OzlParticipation participation = new OzlParticipation((GamesAthlete)participant, this);
            //add new participation to participation set
            _participation.add(participation);
            ((GamesAthlete) participant).addParticipation(participation);
        } else if (participant instanceof GamesOfficial) {
            // add referee, or (if already exists, overwrite)
            // if referee exists, remove reference from this game
            //  remove game from referee if game exist
            removeParticipant(participant);
            _referee = (GamesOfficial) participant;
            ((GamesOfficial) participant).setGame(this);
        }
    }

    // method to remove an athlete or referee
    void removeParticipant(GamesParticipant participant) {
        Predicate<OzlParticipation> predicateAthlete =  p -> p.gamesAthlete.equals(participant);
        if (participant instanceof GamesAthlete && _participation.stream().anyMatch(predicateAthlete)) {
            // identify deprecated participation
            OzlParticipation removableParticipation = _participation.stream()
                    .filter( predicateAthlete )
                    .findAny()
                    .orElseThrow( () -> new IllegalGameException(participant, this));
            // remove participation from athlete
            ((GamesAthlete) participant).removeParticipation(removableParticipation);
            // remove participation from game's collection
            _participation.remove(removableParticipation);
            // removing athletes means game never played with new participants
            if (gamePlayed) gamePlayed = false;
        } else {
            if (_referee != null && _referee.equals(participant)) { // if referee already exists and the same
                _referee.removeGame();
                _referee = null;
            }
        }

    }

    // method to make athletes to compete
    public void gamePlay() throws NotEnoughAthletesException, NoRefereeException {
        if (athletesCount() < MIN_PARTICIPANTS) throw new NotEnoughAthletesException(this);
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
            aParticipation.result = aParticipation.getGamesAthlete().compete(aParticipation);
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
                    .limit(3)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return gameAthletesWinners;
    }

    // get all athletes as list from participation object
    public List<GamesAthlete> getGameAthletes() {
        List<GamesAthlete> gameAthletes = this.getParticipation().stream()
                .filter(Objects::nonNull)
                .map(OzlParticipation::getGamesAthlete)
                .collect(Collectors.toCollection(ArrayList::new));

        return gameAthletes;
    }

    // count total athletes for a game
    Integer athletesCount() {
        return Math.toIntExact(_participation.stream()
                .filter(Objects::nonNull)
                .map(OzlParticipation::getGamesAthlete)
                .count()
        );
    }

}

