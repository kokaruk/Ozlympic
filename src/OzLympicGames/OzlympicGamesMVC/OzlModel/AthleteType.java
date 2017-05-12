package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enumeration of athlete type.
 * @author dimz
 */
public enum AthleteType {
    swimmer(EnumSet.of(GameSports.swimming)),
    cyclist(EnumSet.of(GameSports.cycling)),
    sprinter(EnumSet.of(GameSports.running)),
    superAthlete(EnumSet.allOf(GameSports.class));

    private final Set<GameSports> sports;

    // constructor
    AthleteType(Set<GameSports> sports) {
        this.sports = sports;
    }

    /**
     * Gets collection of sport related to athlete type.
     * @return EnumSet of GameSports
     * @see GameSports
     */
    public Set<GameSports> getSport() {
        return sports;
    }
}