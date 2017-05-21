package ozlympicgames.ozlmodel;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enumeration of athlete type.
 *
 * @author dimz
 * @since 04/04/17
 */
public enum AthleteType {
    /**
     * contains GameSports.swimming
     */
    swimmer(EnumSet.of(GameSports.swimming)),
    /**
     * contains GameSports.cycling
     */
    cyclist(EnumSet.of(GameSports.cycling)),
    /**
     * contains GameSports.running
     */
    sprinter(EnumSet.of(GameSports.running)),
    /**
     * contains EnumSet.allOf(GameSports.class)
     */
    superAthlete(EnumSet.allOf(GameSports.class));

    private final Set<GameSports> sports;

    // constructor
    AthleteType(Set<GameSports> sports) {
        this.sports = sports;
    }

    /**
     * Gets collection of sport related to athlete type.
     *
     * @return EnumSet of GameSports
     * @see GameSports
     */
    public Set<GameSports> getSport() {
        return sports;
    }
}