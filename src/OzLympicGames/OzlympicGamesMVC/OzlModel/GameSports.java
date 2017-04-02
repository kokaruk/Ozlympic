package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 * enumerator sports with values for competing methods
 */
public enum GameSports {
    swimming(100, 200),
    cycling(500, 800),
    running(10, 20);

    // enum accessors
    private final int min;
    private final int max;

    // Constructor
    GameSports(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
