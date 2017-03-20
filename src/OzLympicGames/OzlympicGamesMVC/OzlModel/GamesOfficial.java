package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
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


        return null;
    }
}




    // method to to return winners
    @SuppressWarnings("unchecked")
    private ArrayList<GamesAthlete> getWinners(){
        // reset gameParticipants to total participants, removing Null placeholders
        getMyOzlGame().gameParticipants = Arrays.stream(gameParticipants).filter(Objects::nonNull).toArray(GamesParticipant[]::new);
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
                        .limit(3)
                        .collect(Collectors.toCollection(ArrayList::new));

        int[] awardPoints = new int[]{5, 2, 1};
        for (int i = 0; i < awardPoints.length; i++) {
            ((GamesAthlete)gameWinners.get(i)).setTotalPoints(awardPoints[i]);
        }
        // suppressed warning, stream filter guarantees returned type to be GamAthlete Class
        return (ArrayList<GamesAthlete>)(ArrayList<?>)gameWinners;
    }


/*

ArrayList<GamesAthlete> gameWinners = getWinners();
            String winnersResult = "";
            int counter = 1;
            for (GamesAthlete champion : gameWinners){
                winnersResult += String.format("%1$d: %2$s (%5$s from %6$s).  Result: %3$d seconds. Game Score: %4$d \r\n",
                        counter,
                        champion.getParticipantName(),
                        champion.getLastGameCompeteTime(),
                        champion.getTotalPoints(),
                        Character.toUpperCase(champion.getAthleteType().name().charAt(0)) + champion.getAthleteType().name().substring(1),
                        champion.getParticipantState());
                counter++;
            }
/*
 */