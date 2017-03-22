package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by dimi on 13/3/17.
 */
class GamesOfficialTest {

    String gamesScore;
    GamesParticipant newOfficialSequence;
    OzlGame myOzlGame;
    String participantId;
    final private IOzlGamesORM ormDataReader = OzlGamesORMFake.getInstance();

    @BeforeEach
    void setupOfficial(){
        participantId = "O0001";
        newOfficialSequence = ormDataReader.getGameOfficial(participantId);
        String gameId = "R01";
        myOzlGame = new OzlGame(gameId);
    }

    @Test
    void gameScoreString_notAssigned() {
        String gameWinnersMessage = ((GamesOfficial) newOfficialSequence).gameScore();
        System.out.println(gameWinnersMessage);
        assertTrue(gameWinnersMessage.getClass().equals(String.class));
    }

    @Test
    void gameScoreString_gamePlay_gameNeverPlayed() {
        newOfficialSequence.setMyOzlGame(myOzlGame);
        myOzlGame.setGameParticipants(new GamesParticipant[]{newOfficialSequence});
        String gameWinnersMessage = ((GamesOfficial) newOfficialSequence).gameScore();
        System.out.println(gameWinnersMessage);
        assertTrue(gameWinnersMessage.getClass().equals(String.class));
    }

    @Test
    void gameScoreString_gamePlay_gamePlayed() {
        newOfficialSequence.setMyOzlGame(myOzlGame);
        myOzlGame.setGameParticipants(getOfficialAndAthleteArray());
        myOzlGame.gamePlayGetResults();
        String gameWinnersMessage = ((GamesOfficial) newOfficialSequence).gameScore();
        System.out.println(gameWinnersMessage);
        assertTrue(gameWinnersMessage.getClass().equals(String.class));
    }

    private GamesParticipant getMyNewAthlete(){
        GamesParticipant newAthlete = ormDataReader.getGameAthlete();
        return   Math.toIntExact(((GamesAthlete)newAthlete).getAthleteType().getSport().size()) > 1 ||
                ((GamesAthlete)newAthlete).getAthleteType().getSport().get(0).equals(myOzlGame.getGameSportType()) ? newAthlete : getMyNewAthlete();
    }

    private GamesParticipant[] getOfficialAndAthleteArray(){
        GamesParticipant[] myParticipants = new GamesParticipant[6];
        myParticipants[0] = newOfficialSequence;

        for(int i = 1; i < myParticipants.length; i++) {
            GamesParticipant athlete = getMyNewAthlete();
            athlete.setParticipantId(String.format("%s%03d", Character.toUpperCase(
                    ((GamesAthlete)athlete).getAthleteType().name().charAt(0)),
                    i));
            myParticipants[i] = athlete;
        }
        return myParticipants;
    }

}