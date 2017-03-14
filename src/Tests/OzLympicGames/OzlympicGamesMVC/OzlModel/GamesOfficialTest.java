package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesOfficialTest {

    String gamesScore = "Games Score String";
    OzlGame myOzlGame = new OzlGame("R01");
    GameParticipant newOfficialSequence = new GamesOfficial("Alex Foo", 55);

// see if returns string
    @Test
    void gameScoreString() {
     // assertNotNull(((GamesOfficial) newOfficialSequence).gameScore());
      assertTrue(((GamesOfficial) newOfficialSequence).gameScore().getClass().equals(String.class));
    }
}