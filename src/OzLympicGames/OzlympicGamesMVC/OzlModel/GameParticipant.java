package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
abstract class GameParticipant {

    //field for assigned game. Null if not assigned to a game.
    // can be null, it means the object has been instantiated but not yest assigned to a game
    private OzlGame myOzlGame;
    //setter/getter fot ozl game, should be invoked at time of assignment to a game.
    public void setMyOzlGame(OzlGame myOzlGame) {
        this.myOzlGame = myOzlGame;
    }
    public OzlGame getMyOzlGame() {
        return myOzlGame;
    }

    // field ID and getter / setter. To Be Assigned by Games at the time of assignment to a game
    private String participantId;
    void setParticipantId(String participantId) {
        this.participantId = participantId;
    }
    public String getParticipantId() {    return participantId; }

    // field & public getter for name
    private String participantName;
    public String getParticipantName() {
        return participantName;
    }

    // field and public getter for Age
    private Integer participantAge;
    public Integer getParticipantAge() {
        return participantAge;
    }

    // participant State field and lazy instantiation
    private String participantState;
    public String getParticipantState() {
        if ((this.participantState == null) || (this.participantState.isEmpty())) {
            this.participantState = "Australia";
        }
        return this.participantState;
    }

    // constructor
    public GameParticipant(String participantName, int participantAge) {
        this.participantName = participantName;
        this.participantAge = participantAge;
    }
}


