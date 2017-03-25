package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 * abstrac game participant class
 */
//abstract game participant
abstract class GamesParticipant implements IGameParticipant {

    //property for assigned game. Returns Null if not assigned to a game.
    // Allowed to be null, it means the Participant has been instantiated but not yest assigned to a game
    private OzlGame myOzlGame;
    void setMyOzlGame(OzlGame myOzlGame) {
        this.myOzlGame = myOzlGame;
    }
    OzlGame getMyOzlGame() {
        return myOzlGame;
    }

    // Participant ID. to be set by Games Class at the time of assignment to participants array
    // If GamesOfficial then ID to be set by constructor
    private String participantId;
    @Override
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }
    @Override
    public String getParticipantId() { return participantId; }

    // readOnly field for name
    private final String participantName;
    @Override
    public String getParticipantName() {
        return participantName;
    }

    // field and public getter for Age
    private final Integer participantAge;
    @Override
    public Integer getParticipantAge() {
        return participantAge;
    }

    // participant State field and lazy instantiation
    private final String participantState;
    @Override
    public String getParticipantState() {
        return this.participantState;
    }

    // constructor
    GamesParticipant(String participantName, int participantAge, String participantState) {
        this.participantName = participantName;
        this.participantAge = participantAge;
        this.participantState = participantState;
    }
}