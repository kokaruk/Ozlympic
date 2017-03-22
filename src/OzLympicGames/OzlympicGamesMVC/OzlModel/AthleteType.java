package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dimi on 10/3/17.
 */
// enumerator for athlete type
 enum AthleteType {
    swimmer(Stream.of(GameSports.swimming).collect(Collectors.toList())),
    cyclist(Stream.of(GameSports.cycling).collect(Collectors.toList())),
    sprinter(Stream.of(GameSports.running).collect(Collectors.toList())),
    superAthlete(Stream.of(GameSports.swimming, GameSports.cycling, GameSports.running).collect(Collectors.toList()));

    // enum accessors
    private final List<GameSports> sports;
    public List<GameSports> getSport() {
        return sports;
    }

    // constructor
    AthleteType(List<GameSports> sports){
        this.sports = sports;
    }
}
