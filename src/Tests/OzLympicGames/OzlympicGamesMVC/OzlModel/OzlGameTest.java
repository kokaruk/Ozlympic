package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 16/3/17.
 */
class OzlGameTest {

    OzlGame myOzlGame = new OzlGame("C01");

    @Test
    void getGameSports() {
        assertEquals(GameSports.cycling, myOzlGame.getGameSportType());
    }

    @Test
    void getParticipants() {

    }

    @Test
    void setParticipants() {

    }

    @Test
    void getGameId() {

    }

    @Test
    void getMinParticipants() {

    }

}