package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 12/3/17.
 * Object Relation Mapper. Will speak to dataLayer Factory Class which should return data from DataSource.
 * As Current Project Scope Doesn't require access to XML ot DataBase, this class will contain either hardcoded data
 * or random generation
 */
final class OzlGamesORM {

    private OzlGamesORM() {}

    static OzlGame[] getGames(){

        // some logic and extensive sql that reads from database for the initial game start,
        // if nothing found generates random 10 games
        OzlGame[] myGames = new OzlGame[1];
        myGames[0] = null;

        return myGames;
    }


    // method to generate game official
    static GamesParticipant getGameOfficial(String participantId) {
        GamesParticipant newGameOfficial =
                new GamesOfficial(GamesSharedFunctions.getRandomName(),
                        GamesSharedFunctions.getRandomAge(),
                        participantId,
                        GamesSharedFunctions.getRandomState());
        return newGameOfficial;
    }

    // method to generate game Athlete
    static GamesParticipant getGameAthlete(){
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

