package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GameParticipant {

    //property with private getter for configuration reader.
    //Lazy Instantiates Config Reader Singleton
    // Allows dependency injection for testing
    private IOzlConfigRead configReader;
    public void setConfigReader(IOzlConfigRead configReader) {
        this.configReader = configReader;
    }

    //property for assigned game. Returns Null if not assigned to a game.
    // Allowed to be null, it means the Participant has been instantiated but not yest assigned to a game
    private OzlGame myOzlGame;
    public void setMyOzlGame(OzlGame myOzlGame) {
        this.myOzlGame = myOzlGame;
    }
    public OzlGame getMyOzlGame() {
        return myOzlGame;
    }

    // field ID. Participant ID to be set by Games Class at the time of assignment to participants array
    private String participantId;
    void setParticipantId(String participantId) {
        this.participantId = participantId;
    }
    public String getParticipantId() { return participantId; }

    // readOnly field for name
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
        if ((participantState == null) || (this.participantState.isEmpty())) {
            participantState = configReader.getConfigString("participantDefaultState");
        }
        return this.participantState;
    }

    // constructor
    public GameParticipant(String participantName, int participantAge) {
        this.participantName = participantName;
        this.participantAge = participantAge;
        configReader = OzlConfigRead.getInstance();

    }
    // constructor with ID string
    public GameParticipant(String participantName, int participantAge, String participantId) {
        this(participantName, participantAge);
        this.participantId = participantId;
    }

}


