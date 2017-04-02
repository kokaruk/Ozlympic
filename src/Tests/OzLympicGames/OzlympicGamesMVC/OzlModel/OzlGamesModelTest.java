package OzLympicGames.OzlympicGamesMVC.OzlModel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;



/**
 * Created by dimz on 30/3/17.
 * Test fixture to test games model tom class
 */
class OzlGamesModelTest {

    private IOzlGamesORM ormDataReader;
    private List<IOzlGame> myGames;

    @BeforeEach
    void setup(){
        this.ormDataReader = OzlGamesORMFake.getInstance();
        myGames = ormDataReader.getGames();
    }
    @Test
    void getOzlGames_GetFakeListOfGames_ORMReturns15Games() {

        assertEquals(15, myGames.size() );
    }

    @Test
    void  getParticipants_MakeFakeListOfAthletes_PlayersArrayMoreThan100() {
        List<GamesAthlete> mYgamesAthletes = ormDataReader.getMyGamesAthletes();

        assertTrue(mYgamesAthletes.size() > 100);
    }

}