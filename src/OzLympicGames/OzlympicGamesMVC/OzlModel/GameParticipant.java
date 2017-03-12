package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 10/3/17.
 */
class GameParticipant {
    private String participantId;

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    private String participantName;
    private int participantAge;
    private String participantState;


    public String getParticipantState() {
        if ((participantState != null) && (!participantState.isEmpty())) participantState = "Australia'";
        return participantState;
    }

    public GameParticipant(String participantName, int participantAge) {
    }
}


