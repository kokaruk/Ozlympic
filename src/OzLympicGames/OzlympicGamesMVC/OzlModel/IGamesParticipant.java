package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 19/3/17.
 * GamesParticipant interface.
 * All participants including athletes and officials have:
 * a unique  * ID.
 * Their personal information is also stored
 * including:
 * name
 * age
 * the state (of Australia) they represent.
 */
public interface IGamesParticipant {

    //ID get/set
    void setParticipantId(String participantId);
    String getParticipantId();

    // Get Only Name
    String getParticipantName();

    // Get Only Age
    Integer getParticipantAge();

    // Get Only Participant State
    String getParticipantState();
}
