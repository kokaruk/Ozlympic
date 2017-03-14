package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesAthleteTest {


    GameParticipant newAthlete = new GamesAthlete("Alex Foo", 55);

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
        int minTime = newAthlete.getMyOzlGame().getGameSports().getMin();
        int maxTime = newAthlete.getMyOzlGame().getGameSports().getMax();

        assertTrue( (((GamesAthlete)newAthlete).compete() >= minTime ) && (((GamesAthlete)newAthlete).compete() <= maxTime ) );

    }

}