package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesOfficialTest {

    String gamesScore = "Games Score String";
    OzlGame myOzlGame = new OzlGame();
    GameParticipant newFakeOfficial = new GamesOfficial("Alex Foo", 55);


    @Test
    void gameScore() {
      assertEquals(gamesScore, ((GamesOfficial) newFakeOfficial).gameScore(myOzlGame));
    }

}