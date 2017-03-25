package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 22/3/17.
 * ORM Mapping Interface
 */
interface IOzlGamesORM {

    OzlGame[] getGames();


    // method to generate game official
    GamesParticipant getGameOfficial(String participantId);

    // method to generate game Athlete
    GamesParticipant getGameAthlete();

}
