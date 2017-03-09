/**
 * Created by dimi on 10/3/17.
 */
public class GamesAthlete extends GameParticipant {
    private AtheleteType athleteType;
    private Integer totalPoints;
    private Integer gamePoints;

    public GamesAthlete(String participantId, String participantName, int participantAge) {
        super( participantId,  participantName,  participantAge);
    }

    public Integer compete() {
        return null;
    }
}
