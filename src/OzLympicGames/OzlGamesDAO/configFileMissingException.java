package OzLympicGames.OzlGamesDAO;

import java.io.FileNotFoundException;

/**
 * Thrown if config *.properties file is missing.
 * @author dimz
 * @since 1/4/17
 * @version 2.0
 */
public class configFileMissingException extends FileNotFoundException {

    public configFileMissingException(String missingFile) {
        super(String.format("\033[31mConfig file %s doesn't exist in " +
                "OzLympicGames/OzLympicGamesMVC/OzlGamesDAO \r\n" +
                "make sure to transfer to compile folder from sources", missingFile));
    }
}
