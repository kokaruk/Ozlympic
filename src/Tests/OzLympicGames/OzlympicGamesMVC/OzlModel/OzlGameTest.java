package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigReadFakeAlwaysReturnsHardcodedValues;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Created by dimi on 16/3/17.
 * Test class for OzlGame
 */
class OzlGameTest {

    private String gameId;
    private IOzlGame myOzlGame;
    private String participantId;
    final private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();

    @BeforeEach
    void setupGame(){
        gameId = "C01";
        myOzlGame = new OzlGame(gameId);
        // new fake referee
        participantId = "O01";
    }


    @Test
    void getGameSports_setSportToCycling_ReturnGamSportsCycling() {
        assertEquals(GameSports.cycling, ((OzlGame)myOzlGame).getGameSportType());
    }

    @Test
    void getParticipants_setGameRefereeToFakeReferee_gameRefereeEqualsToFake() {
        IGamesParticipant[] myReferee = new IGamesParticipant[]{ormDataReader.getGameOfficial(participantId)};
        ((OzlGame)myOzlGame).setGameParticipants(myReferee);
        assertEquals(myReferee[0], ((OzlGame)myOzlGame).getGameParticipants()[0]);
    }

    @Test
    void getGameId_setGameIdToFake_ReturnIsTheSame() {
        assertEquals(gameId, myOzlGame.getGameId());
    }

    @Test
    void getMinParticipants_ExpectedFourAsPerConfig() {
        ((OzlGame)myOzlGame).setConfigReader(OzlConfigReadFakeAlwaysReturnsHardcodedValues.getInstance());

        assertEquals(4, myOzlGame.getMinParticipants());
    }

    @Test
    void playGame_ExpectErrorMessageForNotEnoughPlayers() {
        IGamesParticipant[] myReferee = new IGamesParticipant[]{ormDataReader.getGameOfficial(participantId)};
        ((OzlGame)myOzlGame).setGameParticipants(myReferee);
        System.out.println("Players: " + ((OzlGame)myOzlGame).getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players,less than required minimum of %3$d",
                myOzlGame.getGameId(),
                ((OzlGame)myOzlGame).getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.err.println(expectMessage);

        assertEquals(expectMessage, ((OzlGame)myOzlGame).gamePlayGetResults());
    }

    @Test
    void playGame_ExpectErrorMessageForNotEnoughPlayersOnly3() {
        OzlGame  myOzlGame = new OzlGame(gameId);
        IGamesParticipant athlete1 = getMyNewAthlete();
        IGamesParticipant athlete2 = getMyNewAthlete();
        IGamesParticipant athlete3 = getMyNewAthlete();
        IGamesParticipant[] myReferee = new IGamesParticipant[]{ormDataReader.getGameOfficial(participantId),
        athlete1, athlete2, athlete3};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Players: " + myOzlGame.getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players,less than required minimum of %3$d",
                myOzlGame.getGameId(),
                myOzlGame.getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.out.println(expectMessage);

        assertEquals(expectMessage, myOzlGame.gamePlayGetResults());
    }

    @Test
    void playGame_getResultsMessage() {

        ((OzlGame)myOzlGame).setGameParticipants(getOfficialAndAthleteArray());
        System.out.println("Participant (Including Officials): " + ((OzlGame)myOzlGame).getGameParticipants().length);

        String messageResult = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println(messageResult);

        assertTrue(messageResult.getClass().equals(String.class));
    }

    @Test
    void getGamesParticipants_ReturnsNoParticipantsMessage() {
        String warnMessage = ((OzlGame)myOzlGame).getGamePlayersList();
        System.err.println(warnMessage);
        assertTrue(warnMessage.getClass().equals(String.class));
    }

    @Test
    void getGamesParticipants_ReturnsPlayersMessage() {

        ((OzlGame)myOzlGame).setGameParticipants(getOfficialAndAthleteArray());
        String theMessage = ((OzlGame)myOzlGame).getGamePlayersList();
        System.out.println(theMessage);

        assertTrue(theMessage.getClass().equals(String.class));
    }

    @Test
    void setUserPrediction_setPrediction_getCongratsMessage_IntegrationTest(){
        ((OzlGame)myOzlGame).setGameParticipants(getOfficialAndAthleteArray());
        String theMessage = ((OzlGame)myOzlGame).getGamePlayersList();
        System.out.println("Game Players: \n" + theMessage);

        myOzlGame.setUserPrediction(2);
        String gameResults = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println("Game Result: \n" + gameResults);

        String resultFromOfficial = ((GamesOfficial)((OzlGame)myOzlGame).getGameParticipants()[0]).getGameScore();
        System.out.println("Game Winners: \n" + resultFromOfficial);

        System.out.println("Replay \r\n");
        myOzlGame.setUserPrediction(3);
        gameResults = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println("Game Result: \n" + gameResults);
        resultFromOfficial = ((GamesOfficial)((OzlGame)myOzlGame).getGameParticipants()[0]).getGameScore();
        System.out.println("Game Winners: \n" + resultFromOfficial);

        System.out.println("Replay \r\n");
        myOzlGame.setUserPrediction(3);
        gameResults = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println("Game Result: \n" + gameResults);
        resultFromOfficial = ((GamesOfficial)((OzlGame)myOzlGame).getGameParticipants()[0]).getGameScore();
        System.out.println("Game Winners: \n" + resultFromOfficial);

        System.out.println("Replay \r\n");
        myOzlGame.setUserPrediction(3);
        gameResults = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println("Game Result: \n" + gameResults);
        resultFromOfficial = ((GamesOfficial)((OzlGame)myOzlGame).getGameParticipants()[0]).getGameScore();
        System.out.println("Game Winners: \n" + resultFromOfficial);

        System.out.println("Replay \r\n");
        myOzlGame.setUserPrediction(3);
        gameResults = ((OzlGame)myOzlGame).gamePlayGetResults();
        System.out.println("Game Result: \n" + gameResults);
        resultFromOfficial = ((GamesOfficial)((OzlGame)myOzlGame).getGameParticipants()[0]).getGameScore();
        System.out.println("Game Winners: \n" + resultFromOfficial);

        assertTrue(theMessage.getClass().equals(String.class));
    }



    private IGamesParticipant[] getOfficialAndAthleteArray(){
        Integer gameParticipantsBounds = ((OzlGame)myOzlGame).getGameParticipants().length;
        IGamesParticipant[] myParticipants = new GamesParticipant[gameParticipantsBounds];
        myParticipants[0] = ormDataReader.getGameOfficial(participantId);
        ((GamesParticipant)myParticipants[0]).setMyOzlGame(myOzlGame);

        for(int i = 1; i < myParticipants.length; i++) {

            IGamesParticipant athlete = getMyNewAthlete();
            athlete.setParticipantId(String.format("%s%03d", Character.toUpperCase(
                    ((GamesAthlete)athlete).getAthleteType().name().charAt(0)),
                    i));
            myParticipants[i] = athlete;
        }
        return myParticipants;
    }

    private IGamesParticipant getMyNewAthlete(){
        IGamesParticipant newAthlete = ormDataReader.getGameAthlete();
        ((GamesParticipant)newAthlete).setMyOzlGame(myOzlGame);
        // recursive call to get required athlete sports type.
        // not very safe as may potentially run out of names but should work
        return   Math.toIntExact(((GamesAthlete)newAthlete).getAthleteType().getSport().size()) > 1 ||
                ((GamesAthlete)newAthlete).getAthleteType().getSport().contains(((OzlGame)myOzlGame).getGameSportType()) ? newAthlete : getMyNewAthlete();
    }


}