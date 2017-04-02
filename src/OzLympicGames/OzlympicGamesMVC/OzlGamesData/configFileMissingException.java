package OzLympicGames.OzlympicGamesMVC.OzlGamesData;

/**
 * Created by dimz on 1/4/17.
 * exception if required file for config is not present.
 */
public class configFileMissingException extends Exception {

    // throws exception if can't read config file
    private final String missingFile;

    public configFileMissingException(String missingFile) {
        this.missingFile = missingFile;
    }

    public void getMissingFile() {
        System.err.println(
                String.format("\033[31mConfig file %s doesn't exist in " +
                        "OzLympicGames/OzLympicGamesMVC/OzlGamesData \r\n" +
                        "make sure to transfer to compile folder from sources", missingFile)
        );
    }
}
