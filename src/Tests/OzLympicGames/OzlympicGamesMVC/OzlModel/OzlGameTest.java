package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 16/3/17.
 */
class OzlGameTest {

    String gameId;
    OzlGame myOzlGame;
    String participantName;
    Integer participantAge;
    String participantId;



    @BeforeEach
    void setupGame(){
        gameId = "C01";
        myOzlGame = new OzlGame(gameId);
        participantName = "Alex Foo";
        participantAge = 55;
        participantId = "O01";
    }


    @Test
    void getGameSports() {
        assertEquals(GameSports.cycling, myOzlGame.getGameSportType());
    }

    @Test
    void getParticipants() {
        GameParticipant[] myReferee = new GameParticipant[]{new GamesOfficial(participantName, participantAge, participantId)};
        myOzlGame.setParticipants(myReferee);

        assertEquals(myReferee[0], myOzlGame.getParticipants()[0]);
    }

    @Test
    void getGameId() {
        assertEquals("C01", myOzlGame.getGameId());
    }

    @Test
    void getMinParticipants() {
        myOzlGame.setConfigReader(OzlConfigReadFakeAlwaysReturnsHardcodedValues.getInstance());

        assertEquals(4, myOzlGame.getMinParticipants());
    }

}
