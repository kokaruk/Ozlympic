package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 * A Game Class
 */
class OzlGame implements IOzlGame{

    private boolean gameHasBeenPlayed;
    boolean isGameHasBeenPlayed() {
        return gameHasBeenPlayed;
    }

    // property with public setter for configuration reader.
    // Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader;
    void setConfigReader(IOzlConfigRead configReader) {
        this.configReader = configReader;
    }

    // gameSportType of enum. public getter with lazy instantiation
    private GameSports gameSportType;
    GameSports getGameSportType() {
        if (this.gameSportType == null) this.gameSportType = generateSport(gameId);
        return gameSportType;
    }

    // property array of game gameParticipants. participant at index 0 is always the official
    private GamesParticipant[] gameParticipants;
    GamesParticipant[] getGameParticipants() {
        return gameParticipants;
    }
    void setGameParticipants(GamesParticipant[] gameParticipants) {
        this.gameParticipants = gameParticipants;
    }

    // game ID. Public only getter
    private final String gameId;
    @Override
    public String getGameId() {
        return gameId;
    }

    // minimum gameParticipants in a game, sate from config file, populate at init, and getter
    private final int minParticipants;
    public int getMinParticipants() {
        return minParticipants;
    }

    // user prediction. Set to empty if user hasn't predicted
    private String userPrediction = "";
    @Override
    public void setUserPrediction(int userPrediction) {
        if (userPrediction == 0) {
            this.userPrediction = ""; // reset user prediction and get out
            return;
        }
        if (gameParticipants.length > 1 && gameParticipants.length >= userPrediction)
            this.userPrediction = gameParticipants[userPrediction].getParticipantId();
    }
    public String getUserPrediction() {
        return userPrediction;
    }

    // Constructor
    OzlGame(String gameId) {
        // Game ID, to be set by games
        this.gameId = gameId;
        configReader = OzlConfigRead.getInstance();
        minParticipants = configReader.getConfigInt("minParticipants");
        int maxParticipants = configReader.getConfigInt("maxParticipants");
        gameParticipants = new GamesParticipant[maxParticipants+1]; // +1 for index 0 referee
    }

    // Method to Generate GameSports enum based on ID string for constructor
    private GameSports generateSport(String gameId)  {
        String sportsLetter = gameId.substring(0,1).toLowerCase();

        return Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().orElse(null);
    }
    // method to list Athletes
    String getGamePlayersList(){

       int totalPlayers = Math.toIntExact(Arrays.stream(gameParticipants)
                .filter(Objects::nonNull)
                .filter( s -> s instanceof GamesAthlete).count());

        if (totalPlayers > 0){

            List<GamesParticipant> gamePlayers;
            gamePlayers = Arrays.stream(gameParticipants)
                            .filter( s -> s instanceof GamesAthlete)
                            .collect(Collectors.toCollection(ArrayList::new));
            StringBuilder allGamePlayers = new StringBuilder();
            for (GamesParticipant champion : gamePlayers){
                allGamePlayers.append( String.format("%1$s: %2$s (%3$s | %4$s | Age: %5$d) \r\n",
                        champion.getParticipantId(),
                        champion.getParticipantName(),
                        GamesHelperFunctions.firsLetterToUpper(
                                String.join(" ",
                                        ((GamesAthlete)champion).getAthleteType().name().split("(?=\\p{Lu})"))
                        ),
                        champion.getParticipantState(),
                        champion.getParticipantAge())
                );

            }
            return allGamePlayers.toString();
        }
        else {

            return String.format("No Players Assigned to %1$s: %2$s",
                    gameId,
                    GamesHelperFunctions.firsLetterToUpper(this.getGameSportType().name())
            );
        }

    }

    // method to make athlete to compete
    String gamePlayGetResults() {
        //count total athletes assigned to the game
        int totalPlayers = Math.toIntExact(Arrays.stream(gameParticipants)
                                                 .filter(Objects::nonNull)
                                                 .filter( s -> s instanceof GamesAthlete).count());
        //confirm minimum threshold is met, play game
        if (totalPlayers > minParticipants) {
            // string to hold games result
            StringBuilder gameResult = new StringBuilder();
            // Play the game
            List<GamesAthlete> gamePlayers = getPlayersScore();
            //mark game as played
            gameHasBeenPlayed = true;
            // see if user prediction is correct

            int counter = 1;
            // build results string
            for (GamesAthlete champion : gamePlayers){
                gameResult.append( String.format("%1$d: %2$s (%5$s | %6$s | Age: %7$s).  Result: %3$,.2f seconds. Game Score: %4$d \r\n",
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
        }

        else {
            return String.format("The game %1$s has %2$d players, less than required minimum of %3$d", gameId, totalPlayers, minParticipants);
        }
    }
    // method to arrange by finish time, award points to winners
    @SuppressWarnings("unchecked")
    private ArrayList<GamesAthlete> getPlayersScore() {
        // reset gameParticipants to total participants, removing Null placeholders
        gameParticipants = Arrays.stream(gameParticipants).filter(Objects::nonNull).toArray(GamesParticipant[]::new);
        //set Game for each player
        Arrays.stream(gameParticipants).forEach(s -> s.setMyOzlGame(this));
        // Make each athlete compete
        Arrays.stream(gameParticipants).filter( s -> s instanceof GamesAthlete).forEach(s -> ((GamesAthlete) s).compete());

        // arrange by finish time
        Comparator<GamesParticipant> byLastGameTime = Comparator
                .<GamesParticipant>comparingDouble(g1 -> ((GamesAthlete)g1).getLastGameCompeteTime() )
                .thenComparingDouble(g2 -> ((GamesAthlete)g2).getLastGameCompeteTime());
        ArrayList<GamesParticipant> gameWinners =
                Arrays.stream(gameParticipants)
                        .filter( s -> s instanceof GamesAthlete)
                        .sorted(byLastGameTime)
                        .collect(Collectors.toCollection(ArrayList::new));

        // award points
        int[] awardPoints = new int[]{5, 2, 1};
        for (int i = 0; i < awardPoints.length; i++) {
            ((GamesAthlete)gameWinners.get(i)).setTotalPoints(awardPoints[i]);
        }
        // suppressed warning, stream filter guarantees returned type to be GamAthlete Class
        return (ArrayList<GamesAthlete>)(ArrayList<?>)gameWinners;
    }

}

