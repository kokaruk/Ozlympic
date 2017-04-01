package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 * referee class
 *
 */
class GamesOfficial extends GamesParticipant implements IGamesOfficial {

    //constructor
    GamesOfficial(String participantName, int participantAge, String participantId, String participantState) {
        super( participantName,  participantAge, participantState);
        this.setParticipantId(participantId);
    }

    // returns score of a game as a formatted string
    @Override
    public String getGameScore() {
        if (getMyOzlGame() == null){
            return "Games Official not Assigned to a Game, Can't compete yet";
        }  else if (!((OzlGame)getMyOzlGame()).isGameHasBeenPlayed()) {
            return "Game has not been played";
        }  else {
            List<GamesAthlete> gameWinners = getWinners();
            StringBuilder winnersResult = new StringBuilder();
            int counter = 1;
            for (GamesAthlete champion : gameWinners){
                winnersResult.append( String.format("%1$d: %2$s (%5$s | %6$s).  Result: %3$,.2f seconds. Game Score: %4$d \r\n",
                        counter,
                        champion.getParticipantName(),
                        champion.getLastGameCompeteTime(),
                        champion.getTotalPoints(),
                        GamesHelperFunctions.firsLetterToUpper(
                                String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))
                        )
                        ,
                        champion.getParticipantState())
                );
                counter++;
            }
            if (!((OzlGame)getMyOzlGame()).getUserPrediction().isEmpty() &&
                    ((OzlGame)getMyOzlGame()).getUserPrediction().equals(gameWinners.get(0).getParticipantId()) ){
                //add congrats message if picked correct player
                winnersResult.append("\r\nSpot On! You predicted the winner! Well Done!\r\n");
                // reset user prediction
                getMyOzlGame().setUserPrediction(0);
            }
            return winnersResult.toString();
        }
    }

    // method to to return winners
    @SuppressWarnings("unchecked")
    private ArrayList<GamesAthlete> getWinners(){
        //find first three winners
        Comparator<IGamesParticipant> byLastGameTime = Comparator.<IGamesParticipant>comparingDouble(g1 -> ((GamesAthlete)g1).getLastGameCompeteTime() )
                .thenComparingDouble(g2 -> ((GamesAthlete)g2).getLastGameCompeteTime());
        List<IGamesParticipant> gameWinners =
                Arrays.stream(((OzlGame)getMyOzlGame()).getGameParticipants())
                        .filter( s -> s instanceof GamesAthlete)
                        .sorted(byLastGameTime)
                        .limit(3)
                        .collect(Collectors.toCollection(ArrayList::new));
        // suppressed warning, stream filter guarantees returned type to be GamAthlete Class
        return (ArrayList<GamesAthlete>)(ArrayList<?>)gameWinners;
    }
}