package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 14/3/17.
 */
class GameParticipantTest {
    String participantName = "Alex Foo";
    Integer participantAge = 55;
    GameParticipant myParticipant = new GameParticipant(participantName, participantAge);
    OzlGame myOzlGame = new OzlGame("C01");

    @Test
    void getMyOzlGame() {
        myParticipant.setMyOzlGame(myOzlGame);
        assertEquals(myOzlGame, myParticipant.getMyOzlGame());
        myParticipant.setMyOzlGame(null);
    }

    @Test
    void getParticipantId() {
        String someFakeID = "SomeID";
        myParticipant.setParticipantId(someFakeID);
        assertEquals(someFakeID, myParticipant.getParticipantId());
    }

    @Test
    void getParticipantName() {
        assertEquals(participantName, myParticipant.getParticipantName());
    }

    @Test
    void getParticipantAge() {
        assertEquals(participantAge, myParticipant.getParticipantAge());
    }

    @Test
    void getParticipantState() {
        assertEquals("Australia", myParticipant.getParticipantState());
    }

}