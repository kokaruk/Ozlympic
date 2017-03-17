package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesAthleteTest {

    String participantName = "Alex Foo";
    Integer participantAge = 55;

    GameParticipant newAthlete = new GamesAthlete(participantName, participantAge);
    OzlGame myOzlGame = new OzlGame("R01");


    @Test
    void getMyOzlGame() {
        newAthlete.setMyOzlGame(myOzlGame);
        assertEquals(myOzlGame, newAthlete.getMyOzlGame());
        newAthlete.setMyOzlGame(null);
    }

    @Test
    void getParticipantId() {
        String someFakeID = "SomeID";
        newAthlete.setParticipantId(someFakeID);
        assertEquals(someFakeID, newAthlete.getParticipantId());
    }

    @Test
    void getParticipantName() {
        assertEquals(participantName, newAthlete.getParticipantName());
    }

    @Test
    void getParticipantAge() {
        assertEquals(participantAge, newAthlete.getParticipantAge());
    }

    @Test
    void getParticipantState() {
        assertEquals("Australia", newAthlete.getParticipantState());
    }

    @Test
    void getAthleteType() {
        assertTrue(((GamesAthlete) newAthlete).getAthleteType().getClass().equals(AthleteType.class));
    }

    @Test
    void getTotalPoints() {
       assertEquals(new Integer(0), ((GamesAthlete)newAthlete).getTotalPoints());
    }

    @Test
    void compete() throws MyOzlGameNotDefinedException{
        this.newAthlete.setMyOzlGame(myOzlGame);
        int minTime = newAthlete.getMyOzlGame().getGameSportType().getMin();
        int maxTime = newAthlete.getMyOzlGame().getGameSportType().getMax();

        assertTrue( (((GamesAthlete)newAthlete).compete() >= minTime ) && (((GamesAthlete)newAthlete).compete() <= maxTime ) );

    }

}