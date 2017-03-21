package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;



/**
 * Created by dimi on 10/3/17.
 */
class OzlGame implements IOzlGame{

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
    private String gameId;
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
        if (gameParticipants.length > 1 && gameParticipants.length >= userPrediction) this.userPrediction = gameParticipants[userPrediction].getParticipantId();
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

    // Method to Generate GameSports enum based on ID string
    private GameSports generateSport(String gameId)  {
        String sportsLetter = gameId.substring(0,1).toLowerCase();

        return Arrays.stream(GameSports.values()).filter(x -> x.name().startsWith(sportsLetter)).findFirst().get();
    }
    // method to list players for user predictions
    String getGamePlayersList(){

       String NoPlayersMessage = String.format("No Players Assigned to %1$s: %2$s",
                gameId,
                GamesSharedFunctions.firsLetterToUpper(this.getGameSportType().name())
       );

        int totalPlayers = Math.toIntExact(Arrays.stream(gameParticipants)
                .filter(Objects::nonNull)
                .filter( s -> s instanceof GamesAthlete).count());

        if (totalPlayers > 0){
            Comparator<GamesParticipant> byLastGameTime = Comparator.<GamesParticipant>comparingInt(g1 -> ((GamesAthlete)g1).getLastGameCompeteTime() )
                    .thenComparingInt(g2 -> ((GamesAthlete)g2).getLastGameCompeteTime());
            ArrayList<GamesParticipant> gamePlayers =
                    Arrays.stream(gameParticipants)
                            .filter( s -> s instanceof GamesAthlete)
                            .sorted(byLastGameTime)
                            .collect(Collectors.toCollection(ArrayList::new));
            String allGamePlayers = "";
            for (GamesParticipant champion : gamePlayers){
                allGamePlayers += String.format("%1$s: %2$s (%3$s from %4$s). \r\n",
                        champion.getParticipantId(),
                        champion.getParticipantName(),
                        Character.toUpperCase(((GamesAthlete)champion).getAthleteType().name().charAt(0)) +
                                ((GamesAthlete)champion).getAthleteType().name().substring(1),
                        champion.getParticipantState());

            }
            return allGamePlayers;
        }
        else {
            return NoPlayersMessage;
        }

    }


    // method to play game
    String gamePlayGetScore() {
        int totalPlayers = Math.toIntExact(Arrays.stream(gameParticipants)
                                                 .filter(Objects::nonNull)
                                                 .filter( s -> s instanceof GamesAthlete).count());

        if (totalPlayers > minParticipants) {
            ArrayList<GamesAthlete> gamePlayers = getPlayersScore();
            String gameResult = "";
            int counter = 1;
            for (GamesAthlete champion : gamePlayers){
                gameResult += String.format("%1$d: %2$s (%5$s from %6$s).  Result: %3$d seconds. Game Score: %4$d \r\n",
                        counter,
                        champion.getParticipantName(),
                        champion.getLastGameCompeteTime(),
                        champion.getTotalPoints(),
                        GamesSharedFunctions.firsLetterToUpper(champion.getAthleteType().name()),
                        champion.getParticipantState());
                counter++;
            }

            return userPrediction.equals(gamePlayers.get(0).getParticipantId()) ?
                    gameResult + "Spot On! You predicted the winner! Well Done!" :
                    gameResult;
        }

        else {
            return String.format("The game %1$s has %2$d players, less than required minimum of %3$d", gameId, totalPlayers, minParticipants);
        }
    }
    // method to to return winners
    @SuppressWarnings("unchecked")
    private ArrayList<GamesAthlete> getPlayersScore(){
        // reset gameParticipants to total participants, removing Null placeholders
        gameParticipants = Arrays.stream(gameParticipants).filter(Objects::nonNull).toArray(GamesParticipant[]::new);
        //set Game for each player
        Arrays.stream(gameParticipants).forEach(s -> s.setMyOzlGame(this));
        // Make each athlete compete
        Arrays.stream(gameParticipants).filter( s -> s instanceof GamesAthlete).forEach(s -> ((GamesAthlete) s).compete());

        //find first three winners
        Comparator<GamesParticipant> byLastGameTime = Comparator.<GamesParticipant>comparingInt(g1 -> ((GamesAthlete)g1).getLastGameCompeteTime() )
                .thenComparingInt(g2 -> ((GamesAthlete)g2).getLastGameCompeteTime());
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
