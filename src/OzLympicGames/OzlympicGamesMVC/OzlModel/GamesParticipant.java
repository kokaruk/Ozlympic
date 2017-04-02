package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 * abstract game participant class
 */
abstract class GamesParticipant implements IGamesParticipant {

    // readOnly field for name
    private final String participantName;
    // field and public getter for Age
    private final Integer participantAge;
    // participant State field and lazy instantiation
    private final String participantState;
    //property for assigned game. Returns Null if not assigned to a game.
    // Allowed to be null, it means the Participant has been instantiated but not yest assigned to a game
    private IOzlGame myOzlGame;
    // Participant ID. to be set by Games Class at the time of assignment to participants array
    // If GamesOfficial then ID to be set by constructor
    private String participantId;

    // constructor
    GamesParticipant(String participantName, int participantAge, String participantState) {
        this.participantName = participantName;
        this.participantAge = participantAge;
        this.participantState = participantState;
    }

    IOzlGame getMyOzlGame() {
        return myOzlGame;
    }

    void setMyOzlGame(IOzlGame myOzlGame) {
        this.myOzlGame = myOzlGame;
    }

    @Override
    public String getParticipantId() {
        return participantId;
    }

    @Override
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    @Override
    public String getParticipantName() {
        return participantName;
    }

    @Override
    public Integer getParticipantAge() {
        return participantAge;
    }

    @Override
    public String getParticipantState() {
        return this.participantState;
    }
}