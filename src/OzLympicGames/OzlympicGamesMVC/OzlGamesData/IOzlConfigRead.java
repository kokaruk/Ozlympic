package OzLympicGames.OzlympicGamesMVC.OzlGamesData;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.configFileMissingException;

/**
 * Created by dimi on 19/3/17.
 * Interface for Config file Reader
 * Needed for mock dependencies injection for Classes Under Test
 */
public interface IOzlConfigRead {

    @SuppressWarnings("SameParameterValue")
    int getConfigInt(String myPropertyName, String myPropFile);
    String getConfigString(String myPropertyName, String myPropFile) throws configFileMissingException;
}
