package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 * Fake Object Relation Mapper.
 */
final class OzlGamesORMFake implements IOzlGamesORM {


    // private constructor
    private OzlGamesORMFake() {}

    // singleton instance
    private static OzlGamesORMFake instance;
    // lazy instantiation
    static OzlGamesORMFake getInstance(){
        if(instance == null){
            instance = new OzlGamesORMFake();
        }
        return instance;
    }

    @Override
    public OzlGame[] getGames(){

        // some logic and extensive sql that reads from database for the initial game start,
        // if nothing found generates random 10 games
        OzlGame[] myGames = new OzlGame[1];
        myGames[0] = null;

        return myGames;
    }


    // method to generate game official
    @Override
    public GamesParticipant getGameOfficial(String participantId) {
        GamesParticipant newGameOfficial =
                new GamesOfficial(GamesSharedFunctions.getRandomName(),
                        GamesSharedFunctions.getRandomAge(),
                        participantId,
                        GamesSharedFunctions.getRandomState());
        return newGameOfficial;
    }

    // method to generate game Athlete
    @Override
    public GamesParticipant getGameAthlete(){
        GamesParticipant newGameAthlete =
                new GamesAthlete(GamesSharedFunctions.getRandomName(),
                        GamesSharedFunctions.getRandomAge(),
                        GamesSharedFunctions.getRandomState());
        return newGameAthlete;
    }

    private static OzlGame getOzlGame(String gameID) {
        // OzlGame
        return null;
     }
}

