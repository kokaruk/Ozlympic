package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract Game Participant Class
 * @author dimi
 * @version 2.0
 * @since 24/04/17
 */
public abstract class GamesParticipant {

    // Participant ID.
    private final String _id;
    // readOnly field for name
    private final String _name;
    // field and public getter for Age
    private final Integer _age;
    // participant State field and lazy instantiation
    private final String _state;
    // OzlParticipation Association
    private Set<OzlParticipation> myParticipation = new HashSet<>();

    // constructor
    public GamesParticipant(String _id, String _name, int _age, String _state) throws IllegalAustralianStateException {
        // if illegal state, throw
        if (!validState(_state)) throw new IllegalAustralianStateException(_state);
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

    public void addParticipation(OzlParticipation aParticipation) {
        myParticipation.add(aParticipation);
    }

    public void removeParticipation(OzlParticipation aParticipation) {
        if (myParticipation.contains(aParticipation)) myParticipation.remove(aParticipation);
    }

    public Set<OzlParticipation> getMyParticipation() {
        return myParticipation;
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