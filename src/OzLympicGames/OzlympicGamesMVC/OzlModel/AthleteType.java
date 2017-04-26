package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.EnumSet;
import java.util.Set;

/**
 * Created by dimi on 10/3/17.
 * Enumerator for athlete type
 */
public enum AthleteType {
    swimmer(EnumSet.of(GameSports.swimming)),
    cyclist(EnumSet.of(GameSports.cycling)),
    sprinter(EnumSet.of(GameSports.running)),
    superAthlete(EnumSet.allOf(GameSports.class));

    // enum accessors
    private final Set<GameSports> sports;

    // constructor
    AthleteType(Set<GameSports> sports) {
        this.sports = sports;
    }

    public Set<GameSports> getSport() {
        return sports;
    }
}