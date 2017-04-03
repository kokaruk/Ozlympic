package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 * A Game Class
 */
public class OzlGame implements IOzlGame {


    // gameSportType of enum. public getter with lazy instantiation
    private final GameSports gameSportType;
    public GameSports getGameSportType() {
        return gameSportType;
    }
    // game ID. Public only getter
    private final String gameId;
    // minimum gameParticipants in a game, sate from config file, populate at init, and getter
    private final int minParticipants;
    private boolean gameHasBeenPlayed;
    boolean isGameHasBeenPlayed() {
        return gameHasBeenPlayed;
    }
    // property with public setter for configuration reader.
    // Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader = OzlConfigRead.getInstance();
    void setConfigReader(IOzlConfigRead configReader) {
        this.configReader = configReader;
    }
    // property array of game gameParticipants. participant at index 0 is always the official
    private IGamesParticipant[] gameParticipants;
    public IGamesParticipant[] getGameParticipants() {
        return gameParticipants;
    }
    void setAthlete(IGamesParticipant participant){
        int currentEnrollment = GamesHelperFunctions.athletesCount(this);
        // if reached max capacity will ignore all calls
        if (participant instanceof GamesAthlete && currentEnrollment < gameParticipants.length-1){
            gameParticipants[++currentEnrollment] = participant;
        }
    }
    // user prediction. Set to empty if user hasn't predicted
    private String userPrediction = "";

    // Constructor
    OzlGame(String gameId) {
        // Game ID, to be set by games
        this.gameId = gameId;
        minParticipants = configReader.getConfigInt("minParticipants", modelPackageConfig.gamesConfig);
        int maxParticipants = configReader.getConfigInt("maxParticipants", modelPackageConfig.gamesConfig);
        gameParticipants = new IGamesParticipant[maxParticipants + 1]; // +1 for index 0 referee
        gameSportType = generateSport(gameId);
    }

    void setGameParticipants(IGamesParticipant[] gameParticipants) {
        this.gameParticipants = gameParticipants;
    }

    @Override
    public String getGameId() {
        return gameId;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public String getUserPrediction() {
        return userPrediction;
    }

    @Override
    public void setUserPrediction(int userPrediction) {
        if (userPrediction == 0) {
            this.userPrediction = ""; // reset user prediction and get out
            return;
        }

        if (gameParticipants.length > 1 && gameParticipants.length >= userPrediction) {
            this.userPrediction = gameParticipants[userPrediction].getParticipantId();
        }
    }

    // Method to Generate GameSports enum based on ID string for constructor
    private GameSports generateSport(String gameId) {
        String sportsLetter = gameId.substring(0, 1).toLowerCase();

        return Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().orElse(null);
    }

    // method to list Athletes
    public String getGamePlayersList() {

        int totalPlayers = GamesHelperFunctions.athletesCount(this);;

        if (totalPlayers > 0) {

            List<IGamesParticipant> gamePlayers;
            gamePlayers = Arrays.stream(gameParticipants)
                    .filter(GamesAthlete.class::isInstance)
                    .collect(Collectors.toCollection(ArrayList::new));
            StringBuilder allGamePlayers = new StringBuilder();
            int counter = 1;
            for (IGamesParticipant champion : gamePlayers) {
                allGamePlayers.append(String.format("%6$d. %1$s: %2$s (%3$s | %4$s | Age: %5$d) \r\n",
                        champion.getParticipantId(),
                        champion.getParticipantName(),
                        GamesHelperFunctions.firsLetterToUpper(
                                String.join(" ",
                                        ((GamesAthlete) champion).getAthleteType().name().split("(?=\\p{Lu})"))
                        ),
                        champion.getParticipantState(),
                        champion.getParticipantAge(),
                        counter)
                );
                counter++;
            }
            return allGamePlayers.toString();
        } else {

            return String.format("No Players Assigned to %1$s: %2$s",
                    gameId,
                    GamesHelperFunctions.firsLetterToUpper(this.getGameSportType().name())
            );
        }

    }

    // method to make athlete to compete
    public String gamePlayGetResults() {
        //count total athletes assigned to the game
        int totalPlayers = GamesHelperFunctions.athletesCount(this);;
        //confirm minimum threshold is met, play game
        if (totalPlayers >= minParticipants) {
            // string to hold games result
            StringBuilder gameResult = new StringBuilder();
            // Play the game, call getPlayersScore method
            List<GamesAthlete> gamePlayers = getPlayersScore();
            //mark game as played
            gameHasBeenPlayed = true;

            int counter = 1;
            // build results string
            for (GamesAthlete champion : gamePlayers) {
                gameResult.append(String.format("%1$d: %2$s (%5$s | %6$s | Age: %7$s).  Result: %3$,.2f seconds. Total Games Ponts: %4$d \r\n",
                        counter,
                        champion.getParticipantName(),
                        champion.getLastGameCompeteTime(),
                        champion.getTotalPoints(),
                        GamesHelperFunctions.firsLetterToUpper(
                                String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))
                        ),
                        champion.getParticipantState(),
                        champion.getParticipantAge())
                );
                counter++;
            }

            return gameResult.toString();
        } else {
            return String.format("The game %1$s has %2$d players,less than required minimum of %3$d", gameId, totalPlayers, minParticipants);
        }
    }

    // method to arrange by finish time, award points to winners
    private ArrayList<GamesAthlete> getPlayersScore() {
        // reset gameParticipants to total participants, removing Null placeholders
        gameParticipants = Arrays.stream(gameParticipants).filter(Objects::nonNull).toArray(IGamesParticipant[]::new);
        //set Game for each player
        Arrays.stream(gameParticipants).forEach(s -> ((GamesParticipant) s).setMyOzlGame(this));
        // Make each athlete compete
        Arrays.stream(gameParticipants).filter(GamesAthlete.class::isInstance).forEach(s -> ((GamesAthlete) s).compete());

        // arrange by finish time
        Comparator<IGamesParticipant> byLastGameTime = Comparator
                .<IGamesParticipant>comparingDouble(g1 -> ((GamesAthlete) g1).getLastGameCompeteTime())
                .thenComparingDouble(g2 -> ((GamesAthlete) g2).getLastGameCompeteTime());
        ArrayList<GamesAthlete> gameWinners =
                Arrays.stream(gameParticipants)
                        .filter(GamesAthlete.class::isInstance)
                        .map(GamesAthlete.class::cast)
                        .sorted(byLastGameTime)
                        .collect(Collectors.toCollection(ArrayList::new));

        // award points
        int[] awardPoints = new int[]{5, 2, 1};
        for (int i = 0; i < awardPoints.length; i++) {
            ( gameWinners.get(i)).setTotalPoints(awardPoints[i]);
        }
        // suppressed warning, stream filter guarantees returned type to be GamAthlete Class
        return gameWinners;
    }
}

