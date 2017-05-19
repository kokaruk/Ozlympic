package OzLympicGames.OzlModel;

import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlGamesDAL.IOzlConfigRead;
import OzLympicGames.OzlGamesDAL.modelPackageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract Game Participant Class
 *
 * @author dimi
 * @version 2.0
 * @since 24/04/17
 */
public abstract class GamesParticipant {

    private static Logger logger = LogManager.getLogger();
    // Participant ID.
    private final Integer _id;
    // readOnly field for name
    private final String _name;
    // field and public getter for Age
    private final Integer _age;
    // participant State field and lazy instantiation
    private final String _state;


    /**
     * public constructor
     *
     * @throws IllegalAustralianStateException if state string is not an australian state
     * @throws IllegalAgeException             when age f participant is out of permitted bounds
     */
    public GamesParticipant(Integer _id, String _name, int _age, String _state) throws IllegalAustralianStateException,
            IllegalAgeException {
        // if illegal state, throw
        if (!validState(_state)) {
            logger.error("IllegalAustralianStateException thrown");
            throw new IllegalAustralianStateException(_state);
        }
        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        int MIN_AGE = configReader.getConfigInt("MIN_AGE", modelPackageConfig.MODEL_CONFIG_FILE);
        int MAX_AGE = configReader.getConfigInt("MAX_AGE", modelPackageConfig.MODEL_CONFIG_FILE);
        if (_age < MIN_AGE || _age > MAX_AGE) {
            logger.error("IllegalAgeException thrown");
            throw new IllegalAgeException();
        }
        this._id = _id;
        this._name = _name;
        this._age = _age;
        this._state = _state;
    }

    public abstract String getId();
    Integer get_id(){
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Integer getAge() {
        return _age;
    }

    public String getState() {
        return this._state;
    }

    // check if passed string state is correct
    private boolean validState(String aState) {
        Set<String> auStates = new HashSet<>();
        auStates.addAll(
                Arrays.asList("Australian Capital Territory", "New South Wales",
                        "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory")
        );

        return auStates.contains(aState);

    }

}