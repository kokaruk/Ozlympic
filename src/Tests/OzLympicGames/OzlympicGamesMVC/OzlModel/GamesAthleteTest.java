package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesAthleteTest {

    GameParticipant newAthlete;
    OzlGame myOzlGame;
    String participantName;
    Integer participantAge;

    @BeforeEach
    void setupAthlete(){
        participantName = "Alex Foo";
        participantAge = 55;
        newAthlete = new GamesAthlete(participantName, participantAge);
        String gameId = "R01";
        myOzlGame = new OzlGame(gameId);
    }


    @Test
    void getMyOzlGame_newOzlGameClass_AthleteReturnsSameOzlGameObject() {
        newAthlete.setMyOzlGame(myOzlGame);

        assertEquals(myOzlGame, newAthlete.getMyOzlGame());
    }

    @Test
    void getParticipantId_SetParticipantIdString_GetterReturnsSameValue() {
        String someFakeID = ((GamesAthlete)newAthlete).getAthleteType().toString().substring(0,1).toUpperCase() + "01";
        newAthlete.setParticipantId(someFakeID);

        assertEquals(someFakeID, newAthlete.getParticipantId());
    }

    @Test
    void getParticipantName_SetParticipantName_GetterReturnsSameValue() {
        assertEquals(participantName, newAthlete.getParticipantName());
    }

    @Test
    void getParticipantAge_setParticipantAge_GetterReturnsSameValue() {
        assertEquals(participantAge, newAthlete.getParticipantAge());
    }

    @Test
    void getParticipantState_StateReturnsAustralia() {
        newAthlete.setConfigReader(OzlConfigReadFakeAlwaysReturnsHardcodedValues.getInstance());

        assertEquals("Australia", newAthlete.getParticipantState());
    }

    @Test
    void getAthleteType_GameAthleteType_ReturnTypeOfGamesAthlete() {
        assertTrue(((GamesAthlete) newAthlete).getAthleteType().getClass().equals(AthleteType.class));
    }

    @Test
    void getTotalPoints_NewStateAthlete_ReturnsZeroPoints() {
       assertEquals(new Integer(0), ((GamesAthlete)newAthlete).getTotalPoints());
    }

    @Test
    void compete_setOzlGameToAthlete_ReturnedRandomWithinRange_True() throws MyOzlGameNotDefinedException{
        this.newAthlete.setMyOzlGame(myOzlGame);
        int minTime = newAthlete.getMyOzlGame().getGameSportType().getMin();
        int maxTime = newAthlete.getMyOzlGame().getGameSportType().getMax();

        assertTrue( (((GamesAthlete)newAthlete).compete() >= minTime ) && (((GamesAthlete)newAthlete).compete() <= maxTime ) );

    }

}

