package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesOfficialTest {

    String gamesScore;
    GamesParticipant newOfficialSequence;
    OzlGame myOzlGame;
    String participantId;

    @BeforeEach
    void setupOfficial(){
        participantId = "O0001";
        newOfficialSequence = OzlGamesORM.getGameOfficial(participantId);
        String gameId = "R01";
        myOzlGame = new OzlGame(gameId);
    }

// see if returns string
    @Test
    void gameScoreString() {
        String gameWinnersMessage = ((GamesOfficial) newOfficialSequence).gameScore();
        System.out.println(gameWinnersMessage);
        assertTrue(gameWinnersMessage.getClass().equals(String.class));
    }
}