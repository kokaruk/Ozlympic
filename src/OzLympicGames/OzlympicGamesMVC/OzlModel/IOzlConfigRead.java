package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 19/3/17.
 */
public interface IOzlConfigRead {

    int getConfigInt(String myPropertyName);
    String getConfigString(String myPropertyName);
}
