package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GameParticipant {

    //field for assigned game. Null if not assigned to a game.
    // can be null, it means the object has been instantiated but not yest assigned to a game
    private OzlGame myOzlGame;
    //setter fot ozl game, should be invoked at time of assignment to a game
    public void setMyOzlGame(OzlGame myOzlGame) {
        this.myOzlGame = myOzlGame;
    }
    // getter for my game
    public OzlGame getMyOzlGame() {
        return myOzlGame;
    }

    // field ID and public setter
    private String participantId;
    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    // field & public getter for name
    private String participantName;
    public String getParticipantName() {
        return participantName;
    }

    // field and public getter for Age
    private int participantAge;
    public int getParticipantAge() {
        return participantAge;
    }

    // participant State field and lazy instantiation
    private String participantState;
    public String getParticipantState() {
        if ((participantState != null) && (!participantState.isEmpty())) participantState = "Australia'";
        return participantState;
    }

    // constructor
    public GameParticipant(String participantName, int participantAge) {
    }
}


