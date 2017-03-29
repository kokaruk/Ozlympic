package OzLympicGames.OzlympicGamesMVC.OzlModel;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by dimi on 10/3/17.
 * Enumerator for athlete type
 */
enum AthleteType {
    swimmer( EnumSet.of(GameSports.swimming)),
    cyclist( EnumSet.of(GameSports.cycling)) ,
    sprinter(EnumSet.of(GameSports.running)),
    superAthlete(EnumSet.allOf(GameSports.class) );

    // enum accessors
    private final Set<GameSports> sports;
    public Set<GameSports> getSport() {
        return sports;
    }

    // constructor
    AthleteType(Set<GameSports> sports){
        this.sports = sports;
    }
}