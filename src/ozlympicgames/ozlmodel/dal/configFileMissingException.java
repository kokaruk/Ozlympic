package ozlympicgames.ozlmodel.dal;

import java.io.FileNotFoundException;

/**
 * Thrown if config *.properties file is missing.
 *
 * @author dimz
 * @version 2.0
 * @since 1/4/17
 */
public class configFileMissingException extends FileNotFoundException {

    public configFileMissingException(String missingFile) {
        super(String.format("\033[31mConfig file %s doesn't exist in " +
                "OzLympicGames/OzLympicGamesMVC/dal \r\n" +
                "make sure to transfer to compile folder from sources", missingFile));
    }
}
