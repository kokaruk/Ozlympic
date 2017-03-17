package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 16/3/17.
 */
class OzlGameTest {

    String gameId = "C01";
    OzlGame myOzlGame = new OzlGame(gameId);

    @Test
    void getGameSports() {
        assertEquals(GameSports.cycling, myOzlGame.getGameSportType());
    }

    @Test
    void getParticipants() {
        GameParticipant[] mYreferee = new GameParticipant[]{new GamesOfficial("Alex Foo", 55)};
        myOzlGame.setParticipants(mYreferee);

        assertEquals(mYreferee[0], myOzlGame.getParticipants()[0]);
    }

    @Test
    void getGameId() {
        assertEquals("C01", myOzlGame.getGameId());
    }

    @Test
    void getMinParticipants() {
        assertEquals(4, myOzlGame.getMinParticipants());
    }

}