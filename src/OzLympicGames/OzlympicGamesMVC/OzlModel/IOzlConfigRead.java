package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 19/3/17.
 * Interface for Config file Reader
 * Needed for dependencies injection for Testing Purposes for Classes Under Test
 */
interface IOzlConfigRead {

    int getConfigInt(String myPropertyName);
    String getConfigString(String myPropertyName);
}
