package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
 enum GameSports {
    swimming(100, 200),
    cycling (500, 800),
    running(10, 20);

    private final int min;
    private final int max;
    GameSports(int min, int max){
        this.min = min;
        this.max = max;
    }
}
