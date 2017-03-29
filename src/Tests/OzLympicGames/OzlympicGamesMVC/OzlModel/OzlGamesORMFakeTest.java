package OzLympicGames.OzlympicGamesMVC.OzlModel;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;



/**
 * Created by dimz on 30/3/17.
 */
class OzlGamesORMFakeTest {


    @Test
    void getOzlGames_GetFakeListOfGames_ORMReturns15Games() {
        IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
        List<IOzlGame> myGames = ormDataReader.getGames();
        assertEquals(15, myGames.size() );
    }

    @Test
    void  getParticipants_MakeFakeListOfAthletes_PlayersArrayMoreThan100() {
        IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();
        List<GamesAthlete> mYgamesAthletes = ormDataReader.getMyGamesAthletes();
        assertTrue(mYgamesAthletes.size() > 100);
    }

}