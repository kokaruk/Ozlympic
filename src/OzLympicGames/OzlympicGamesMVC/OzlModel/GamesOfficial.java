package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 */
class GamesOfficial extends GamesParticipant implements IGamesOfficial {

    //constructor
    GamesOfficial(String participantName, int participantAge, String participantId, String participantState) {
        super( participantName,  participantAge, participantState);
        this.setParticipantId(participantId);
    }

    // returns score of a game as a formatted string
    @Override
    public String gameScore() {
        if (getMyOzlGame() == null){
           return "Games Official not Assigned to a Game, Can't compete yet";
        }
        else if (!getMyOzlGame().isGameHasBeenPlayed()) {
            return "Game has not been played";
        }
        else {
            List<GamesAthlete> gameWinners = getWinners();
            String winnersResult = "";
            int counter = 1;
            for (GamesAthlete champion : gameWinners){
                winnersResult += String.format("%1$d: %2$s (%5$s from %6$s).  Result: %3$d seconds. Game Score: %4$d \r\n",
                        counter,
                        champion.getParticipantName(),
                        champion.getLastGameCompeteTime(),
                        champion.getTotalPoints(),
                        GamesSharedFunctions.firsLetterToUpper(
                                String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))
                        )
                        ,
                        champion.getParticipantState());
                counter++;
            }
            return winnersResult;
        }
    }

    // method to to return winners
    @SuppressWarnings("unchecked")
    private ArrayList<GamesAthlete> getWinners(){
        //find first three winners
        Comparator<GamesParticipant> byLastGameTime = Comparator.<GamesParticipant>comparingInt(g1 -> ((GamesAthlete)g1).getLastGameCompeteTime() )
                .thenComparingInt(g2 -> ((GamesAthlete)g2).getLastGameCompeteTime());
        ArrayList<GamesParticipant> gameWinners =
                Arrays.stream(getMyOzlGame().getGameParticipants())
                        .filter( s -> s instanceof GamesAthlete)
                        .sorted(byLastGameTime)
                        .limit(3)
                        .collect(Collectors.toCollection(ArrayList::new));
        // suppressed warning, stream filter guarantees returned type to be GamAthlete Class
        return (ArrayList<GamesAthlete>)(ArrayList<?>)gameWinners;
    }
}