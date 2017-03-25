package OzLympicGames.OzlympicGamesMVC.OzlModel;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dimi on 10/3/17.
 * Enumerator for athlete type
 */

enum AthleteType {
    swimmer( Stream.of(GameSports.swimming).collect(Collectors.toCollection(ArrayList<GameSports>::new))),
    cyclist( Stream.of(GameSports.cycling).collect(Collectors.toCollection(ArrayList<GameSports>::new))) ,
    sprinter(Stream.of(GameSports.running).collect(Collectors.toCollection(ArrayList<GameSports>::new))),
    superAthlete(Stream.of(GameSports.cycling, GameSports.running, GameSports.swimming).collect(Collectors.toCollection(ArrayList<GameSports>::new)));

    // enum accessors
    private final List<GameSports> sports;
    public List<GameSports> getSport() {
        return sports;
    }

    // constructor
    AthleteType(ArrayList<GameSports> sports){
        this.sports = sports;
    }
}