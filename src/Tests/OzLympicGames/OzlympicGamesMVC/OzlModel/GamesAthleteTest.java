package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 * Athlete Participant Test
 */
class GamesAthleteTest {

    private IGamesParticipant newAthlete;
    private IOzlGame myOzlGame;
    final private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
    private String someFakeID;

    @BeforeEach
    void setupAthlete(){
        someFakeID = "S001";
        newAthlete = ormDataReader.getGameAthlete();
        newAthlete.setParticipantId(someFakeID);
        String gameId = "R01";
        myOzlGame = new OzlGame(gameId);
        ((GamesParticipant)newAthlete).setMyOzlGame(myOzlGame);
    }


    @Test
    void getMyOzlGame_newOzlGameClass_AthleteReturnsSameOzlGameObject() {

        assertEquals(myOzlGame, ((GamesParticipant)newAthlete).getMyOzlGame());
    }

    @Test
    void getParticipantId_SetParticipantIdString_GetterReturnsSameValue() {

        assertEquals(someFakeID, newAthlete.getParticipantId());
    }


    @Test
    void getAthleteType_GameAthleteType_ReturnTypeOfGamesAthlete() {
        assertTrue(((GamesAthlete) newAthlete).getAthleteType().getClass().equals(AthleteType.class));
    }

    @Test
    void getTotalPoints_NewAthlete_ReturnsZeroPoints() {
       assertEquals(new Integer(0), ((GamesAthlete)newAthlete).getTotalPoints());
    }

    @Test
    void compete_setOzlGameToAthlete_ReturnedRandomWithinRange_True(){
        ((GamesParticipant)newAthlete).setMyOzlGame(myOzlGame);
        int minTime = ((OzlGame)((GamesParticipant)newAthlete).getMyOzlGame()).getGameSportType().getMin();
        int maxTime = ((OzlGame)((GamesParticipant)newAthlete).getMyOzlGame()).getGameSportType().getMax();

        assertTrue( (((GamesAthlete)newAthlete).compete() >= minTime ) && (((GamesAthlete)newAthlete).compete() <= maxTime ) );

    }

}

