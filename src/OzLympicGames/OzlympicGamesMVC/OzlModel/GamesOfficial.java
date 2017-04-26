package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dimi on 10/3/17.
 * referee class
 */
public class GamesOfficial extends GamesParticipant {

    //constructor
    public GamesOfficial(String _id, String _name, int _age, String _state) {
        super(_id, _name, _age, _state);
    }

    // returns comma separated list of string of a game as a formatted string
    public List<String> getGameScore(OzlGame currentGame) throws IllegalGameException, GameNeverPlayedException {
            if(!getMyParticipation().contains(currentGame))throw new IllegalGameException(this, currentGame);
            if(!currentGame.isReplay()) throw new GameNeverPlayedException(currentGame);
            List<String> winnersScore = new ArrayList<>();
            //static call
            List<GamesAthlete> gameWinners = GamesOfficial.getWinners(currentGame);
            for (GamesAthlete champion : gameWinners) {
                StringBuilder winnersResult = new StringBuilder();
                winnersResult.append(champion.getId().concat(","));
                winnersResult.append(champion.getName().concat(","));
                winnersResult.append(champion.getState().concat(","));
                winnersResult.append(String.format("%.2f", champion.getLastGameCompeteTime()).concat(","));
                winnersResult.append(champion.getTotalPoints().toString().concat(","));
                winnersResult.append(GamesHelperFunctions.firsLetterToUpper(
                        String.join(" ", champion.getAthleteType().name().split("(?=\\p{Lu})"))).concat(","));

                winnersScore.add(winnersResult.toString());
            }
            return winnersScore;
    }

    // static method to to return winners
    private static List<GamesAthlete> getWinners(OzlGame currentGame) {
        // get winners by finish time
        List<GamesAthlete> gameWinners = GamesHelperFunctions.getGameAthletesWinnersSortedByTime(currentGame);
        // award points
        int[] awardPoints = new int[]{5, 2, 1};
        for (int i = 0; i < awardPoints.length; i++) {
            (gameWinners.get(i)).setTotalPoints(awardPoints[i]);
        }
        return gameWinners;
    }
}