package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 16/3/17.
 */
class OzlGameTest {

    String gameId;
    OzlGame myOzlGame;
    String participantId;

    @BeforeEach
    void setupGame(){
        gameId = "C01";
        myOzlGame = new OzlGame(gameId);
        // new fake referee
        participantId = "O01";
    }


    @Test
    void getGameSports_setSportToCycling_ReturnGamSportsCycling() {
        assertEquals(GameSports.cycling, myOzlGame.getGameSportType());
    }

    @Test
    void getParticipants_setGameRefereeTofakeReferee_gameRefereeEqualsToFake() {
        GamesParticipant[] myReferee = new GamesParticipant[]{OzlGamesORM.getGameOfficial(participantId)};
        myOzlGame.setGameParticipants(myReferee);
        assertEquals(myReferee[0], myOzlGame.getGameParticipants()[0]);
    }

    @Test
    void getGameId_setGameIdToFake_ReturnIsTheSame() {
        assertEquals(gameId, myOzlGame.getGameId());
    }

    @Test
    void getMinParticipants_ExpectedFourAsPerconfig() {
        myOzlGame.setConfigReader(OzlConfigReadFakeAlwaysReturnsHardcodedValues.getInstance());

        assertEquals(4, myOzlGame.getMinParticipants());
    }

    @Test
    void playGame_ExpectErrorMessageForNotEnoughPlayers() {
        GamesParticipant[] myReferee = new GamesParticipant[]{OzlGamesORM.getGameOfficial(participantId)};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Players: " + myOzlGame.getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players, less than required minimum of %3$d",
                myOzlGame.getGameId(),
                myOzlGame.getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.out.println(expectMessage);

        assertEquals(expectMessage, myOzlGame.gamePlayGetScore());
    }

    @Test
    void playGame_ExpectErrorMessageForNotEnoughPlayersOnly3() {
        GamesParticipant athlete1 = getMyNewAthlete("cyclist");
        GamesParticipant athlete2 = getMyNewAthlete("cyclist");
        GamesParticipant athlete3 = getMyNewAthlete("cyclist");
        GamesParticipant[] myReferee = new GamesParticipant[]{OzlGamesORM.getGameOfficial(participantId),
        athlete1, athlete2, athlete3};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Players: " + myOzlGame.getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players, less than required minimum of %3$d",
                myOzlGame.getGameId(),
                myOzlGame.getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.out.println(expectMessage);

        assertEquals(expectMessage, myOzlGame.gamePlayGetScore());
    }

    @Test
    void playGame_getResultsMessage() {
        GamesParticipant athlete1 = getMyNewAthlete("cyclist");
        GamesParticipant athlete2 = getMyNewAthlete("cyclist");
        GamesParticipant athlete3 = getMyNewAthlete("cyclist");
        GamesParticipant athlete4 = getMyNewAthlete("cyclist");
        GamesParticipant athlete5 = getMyNewAthlete("cyclist");
        GamesParticipant[] myReferee = new GamesParticipant[]{OzlGamesORM.getGameOfficial(participantId),
                athlete1, athlete2, athlete3, athlete4, athlete5};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Participant (Including Officials): " + myOzlGame.getGameParticipants().length);

        String messageResult = myOzlGame.gamePlayGetScore();
        System.out.println(messageResult);
        assertTrue(messageResult.getClass().equals(String.class));
    }



    private GamesParticipant getMyNewAthlete(String athleteTypeString){
        GamesParticipant newAthlete = OzlGamesORM.getGameAthlete();
        return ((GamesAthlete)newAthlete).getAthleteType().name().equals(athleteTypeString) ? newAthlete : getMyNewAthlete(athleteTypeString);
    }


}