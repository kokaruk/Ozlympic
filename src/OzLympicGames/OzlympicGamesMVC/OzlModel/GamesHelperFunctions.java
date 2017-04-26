package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlModel.GamesAthlete;
import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGame;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 12/3/17.
 * Static shared functions for classes
 * class is final and cannot be instantiated
 */
public final class GamesHelperFunctions {

    // private constructor
    private GamesHelperFunctions() {
    }

    // Java 8 method to randomly generate an integer within passed set range
    public static int getRandomNumberInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // method to randomly generate an double within passed set range
    public static double getRandomDoubleInRange(int min, int max) {
        return min + new Random().nextDouble() * (max - min);
    }

    //method to capitalise first letter in string
    public static String firsLetterToUpper(String myString) {
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

    // count total athletes for a game
    public static Integer athletesCount(OzlGame game){
        return Math.toIntExact( game.getParticipation().stream()
                .filter(Objects::nonNull)
                .map(OzlParticipation::getGamesParticipant)
                .filter(GamesAthlete.class::isInstance).count()
        );
    }

    public static List<GamesAthlete> getGameAthletes(OzlGame game){
        List<GamesAthlete> gameAthletes = new ArrayList<>();
        if ( GamesHelperFunctions.athletesCount(game) > 0) {
            gameAthletes = game.getParticipation().stream()
                    .filter(Objects::nonNull)
                    .map(OzlParticipation::getGamesParticipant)
                    .filter(GamesAthlete.class::isInstance)
                    .map(GamesAthlete.class::cast)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return gameAthletes;
    }

    public static List<GamesAthlete> getGameAthletesWinnersSortedByTime(OzlGame game){
        List<GamesAthlete> gameAthletes = new ArrayList<>();
        if ( GamesHelperFunctions.athletesCount(game) > 0) {
            Comparator<OzlParticipation> byLastGameTime = Comparator
                    .<OzlParticipation>comparingDouble(g1 -> g1.result)
                    .thenComparingDouble(g2 -> g2.result);

            gameAthletes = game.getParticipation().stream()
                    .filter(Objects::nonNull)
                    .sorted(byLastGameTime)
                    .map(OzlParticipation::getGamesParticipant)
                    .filter(GamesAthlete.class::isInstance)
                    .map(GamesAthlete.class::cast)
                    .limit(3)
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return gameAthletes;
    }

    // create new participation class and assign members to it
    public static void createParticipation (GamesParticipant gamesParticipant, OzlGame ozlGame){
        // TODO check and throw if wrong sport
        OzlParticipation participation = new OzlParticipation(gamesParticipant, ozlGame);
        gamesParticipant.addParticipation(participation);
        ozlGame.addParticipation(participation);
    }

}
