package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



/**
 * Created by dimi on 16/3/17.
 */
class OzlGameTest {

    private String gameId;
    private OzlGame myOzlGame;
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
        assertEquals(GameSports.cycling, myOzlGame.getGameSportType());
    }

    @Test
    void getParticipants_setGameRefereeToFakeReferee_gameRefereeEqualsToFake() {
        GamesParticipant[] myReferee = new GamesParticipant[]{ormDataReader.getGameOfficial(participantId)};
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
        GamesParticipant[] myReferee = new GamesParticipant[]{ormDataReader.getGameOfficial(participantId)};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Players: " + myOzlGame.getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players, less than required minimum of %3$d",
                myOzlGame.getGameId(),
                myOzlGame.getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.out.println(expectMessage);

        assertEquals(expectMessage, myOzlGame.gamePlayGetResults());
    }

    @Test
    void playGame_ExpectErrorMessageForNotEnoughPlayersOnly3() {
        GamesParticipant athlete1 = getMyNewAthlete();
        GamesParticipant athlete2 = getMyNewAthlete();
        GamesParticipant athlete3 = getMyNewAthlete();
        GamesParticipant[] myReferee = new GamesParticipant[]{ormDataReader.getGameOfficial(participantId),
        athlete1, athlete2, athlete3};
        myOzlGame.setGameParticipants(myReferee);
        System.out.println("Players: " + myOzlGame.getGameParticipants().length);
        String expectMessage = String.format("The game %1$s has %2$d players, less than required minimum of %3$d",
                myOzlGame.getGameId(),
                myOzlGame.getGameParticipants().length-1,
                myOzlGame.getMinParticipants());
        System.out.println(expectMessage);

        assertEquals(expectMessage, myOzlGame.gamePlayGetResults());
    }

    @Test
    void playGame_getResultsMessage() {

        myOzlGame.setGameParticipants(getOfficialAndAthleteArray());
        System.out.println("Participant (Including Officials): " + myOzlGame.getGameParticipants().length);

        String messageResult = myOzlGame.gamePlayGetResults();
        System.out.println(messageResult);

        assertTrue(messageResult.getClass().equals(String.class));
    }

    @Test
    void getGamesParticipants_ReturnsNoParticipantsMessage() {
        String warnMessage = myOzlGame.getGamePlayersList();
        System.out.println(warnMessage);
        assertTrue(warnMessage.getClass().equals(String.class));
    }
    @Test
    void getGamesParticipants_ReturnsPlayersMessage() {

        myOzlGame.setGameParticipants(getOfficialAndAthleteArray());

        String theMessage = myOzlGame.getGamePlayersList();
        System.out.println(theMessage);
        assertTrue(theMessage.getClass().equals(String.class));
    }


    private GamesParticipant[] getOfficialAndAthleteArray(){
        Integer gameParticipantsBounds = myOzlGame.getGameParticipants().length;
        GamesParticipant[] myParticipants = new GamesParticipant[gameParticipantsBounds];
        myParticipants[0] = ormDataReader.getGameOfficial(participantId);

        for(int i = 1; i < myParticipants.length; i++) {
            GamesParticipant athlete = getMyNewAthlete();
            athlete.setParticipantId(String.format("%s%03d", Character.toUpperCase(
                    ((GamesAthlete)athlete).getAthleteType().name().charAt(0)),
                    i));
            myParticipants[i] = athlete;
        }
        return myParticipants;
    }

    private GamesParticipant getMyNewAthlete(){
        GamesParticipant newAthlete = ormDataReader.getGameAthlete();
        return   Math.toIntExact(((GamesAthlete)newAthlete).getAthleteType().getSport().size()) > 1 ||
                ((GamesAthlete)newAthlete).getAthleteType().getSport().get(0).equals(myOzlGame.getGameSportType()) ? newAthlete : getMyNewAthlete();
    }


}