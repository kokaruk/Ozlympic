package ozlympicgames.ozlmodel;

/**
 * Enumeration of sports with values for competing methods.
 *
 * @author dimz
 * @since 10/3/17
 */
public enum GameSports {
    /**
     * Min 100, Max 200
     */
    swimming(100, 200),
    /**
     * Min 500, Max 800
     */
    cycling(500, 800),
    /**
     * Min 10, Max 20
     */
    running(10, 20);

    // enum accessors
    private final int min;
    private final int max;

    // Constructor
    GameSports(int min, int max) {
        this.min = min;
        this.max = max;
    }

    /**
     * @return Mimum bound
     */
    public int getMin() {
        return min;
    }

    /**
     * @return Maximum bound
     */
    public int getMax() {
        return max;
    }
}
