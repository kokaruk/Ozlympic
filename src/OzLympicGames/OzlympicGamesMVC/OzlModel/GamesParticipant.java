package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.modelPackageConfig;

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
    private final int MIN_AGE;
    private final int MAX_AGE;

    // Participant ID.
    private final String _id;
    // readOnly field for name
    private final String _name;
    // field and public getter for Age
    private final Integer _age;
    // participant State field and lazy instantiation
    private final String _state;


    // constructor
    public GamesParticipant(String _id, String _name, int _age, String _state) throws IllegalAustralianStateException,
            IlleagalAgeException {
        // if illegal state, throw
        if (!validState(_state)) throw new IllegalAustralianStateException(_state);
        IOzlConfigRead configReader = GamesHelperFunctions.getConfigReader();
        MIN_AGE = configReader.getConfigInt("MIN_AGE", modelPackageConfig.MODEL_COFIG_FILE);
        MAX_AGE = configReader.getConfigInt("MAX_AGE", modelPackageConfig.MODEL_COFIG_FILE);
        if (_age < MIN_AGE || _age > MAX_AGE) throw new IlleagalAgeException();
        this._id = _id;
        this._name = _name;
        this._age = _age;
        this._state = _state;
    }

    public String getId() {
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