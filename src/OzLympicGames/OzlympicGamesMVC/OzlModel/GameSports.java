package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 * enumerator  sports with values for competing methods
 */

 enum GameSports {
    swimming(100, 200),
    cycling (500, 800),
    running(10, 20);

    // enum accessors
    private final int min;
    public int getMin() {
        return min;
    }

    private final int max;
    public int getMax() {
        return max;
    }

    // Constructor
    GameSports(int min, int max){
        this.min = min;
        this.max = max;
    }
}
