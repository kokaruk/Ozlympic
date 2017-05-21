package ozlympicgames.ozlmodel;

import ozlympicgames.ozlmodel.dal.IOzlConfigRead;
import ozlympicgames.ozlmodel.dal.modelPackageConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

;


/**
 * @author dimz
 * @since 7/5/17.
 */
class OzlGameTest {

    @Mock
    private static IOzlConfigRead configReaderMock;
    OzlGame testGame;

    @BeforeEach
    void setUp() {
        configReaderMock = mock(IOzlConfigRead.class);
        // insert into seam of config reader
        GamesHelperFunctions.setCustomReader(configReaderMock);
    }

    @AfterEach
    void tearDown() {
        GamesHelperFunctions.setCustomReader(null);
    }

    @Nested
    @DisplayName("gamePlay method test")
    class gamePlayTest{

        @Mock GamesAthlete sprinter1;
        @Mock GamesAthlete sprinter2;
        @Mock GamesAthlete superAthlete1;
        @Mock GamesOfficial referee;

        List<GamesAthlete> testAthletes = new ArrayList<>();

        ArgumentCaptor<Integer> argInt;
        ArgumentCaptor<OzlParticipation> argParticipation;

        @BeforeEach
        void setUp(){
            //arg captors
            argInt = ArgumentCaptor.forClass(Integer.class);
            argParticipation = ArgumentCaptor.forClass(OzlParticipation.class);
            MockitoAnnotations.initMocks(this);
            when(configReaderMock.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(3);
            when(configReaderMock.getConfigInt("MAX_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(4);
            //Game.Id
            String GameSportsId = idBuilder(AthleteType.sprinter.getSport().iterator().next().name());
            testGame = new OzlGame(GameSportsId);

            // sprinter1
            when(sprinter1.getAthleteType()).thenReturn(AthleteType.sprinter);
            doNothing().when(sprinter1).setTotalPoints(argInt.capture());
            testGame.addParticipant(sprinter1);
            when(sprinter1.compete(argParticipation.capture())).thenReturn(12.05);
            // sprinter2
            when(sprinter2.getAthleteType()).thenReturn(AthleteType.sprinter);
            doNothing().when(sprinter2).setTotalPoints(argInt.capture());
            testGame.addParticipant(sprinter2);
            when(sprinter2.compete(argParticipation.capture())).thenReturn(13.05);
            // superAthlete1
            when(superAthlete1.getAthleteType()).thenReturn(AthleteType.superAthlete);
            doNothing().when(superAthlete1).setTotalPoints(argInt.capture());
            testGame.addParticipant(superAthlete1);
            when(superAthlete1.compete(argParticipation.capture())).thenReturn(14.05);
            // referee
            doNothing().when(referee).awardPoints(testGame);
            testGame.addParticipant(referee);
        }

        @DisplayName("Make participants compete assert game status is played")
        @Test
        void gamePlay_allCorrect_gamePlayedTrue(){
            // assert is new game before compete
            assertFalse(testGame.isGamePlayed());
            testGame.gamePlay();

            assertTrue(testGame.isGamePlayed());
        }

        @DisplayName("Make participants compete verify compete method is called")
        @Test
        void gamePlay_allCorrect_SuperAthleteAsArgument(){
            testGame.gamePlay();
            verify(superAthlete1).compete(argParticipation.capture());
            verify(superAthlete1, times(1)).compete(argParticipation.getValue());

            assertEquals(superAthlete1, argParticipation.getValue().gamesAthlete);
        }

        @DisplayName("Make participants compete verify referee award points is called")
        @Test
        void gamePlay_allCorrect_RefereeAwardPoints(){
            testGame.gamePlay();

            verify(referee, times(1)).awardPoints(testGame);
        }

        @Nested
        @DisplayName("gamePlay exceptions tests")
        class gamePlayExceptions {
            @DisplayName("gamePlay throws NotEnoughAthletesException")
            @Test
            void gamePlay_NotEnoughAthletes_ThrowsNotEnoughAthletesException() {
                when(configReaderMock.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(4);
                String GameSportsId = idBuilder(AthleteType.sprinter.getSport().iterator().next().name());
                testGame = new OzlGame(GameSportsId);
                testGame.addParticipant(sprinter1);
                testGame.addParticipant(sprinter2);
                testGame.addParticipant(superAthlete1);
                Throwable exception = assertThrows(NotEnoughAthletesException.class, () -> testGame.gamePlay());
                String expected = String.format("Not enough players to play the game %s : %s. Only %d Athletes recorded",
                        testGame.getId(),
                        GamesHelperFunctions.firsLetterToUpper(testGame.getGameSport().toString()),
                        testGame.athletesCount());
                assertEquals(expected, exception.getMessage());
            }
            @DisplayName("gamePlay throws NoRefereeException")
            @Test
            void gamePlay_NoReferee_ThrowsNoRefereeException() {
                when(configReaderMock.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(2);
                String GameSportsId = idBuilder(AthleteType.sprinter.getSport().iterator().next().name());
                testGame = new OzlGame(GameSportsId);
                testGame.addParticipant(sprinter1);
                testGame.addParticipant(sprinter2);
                testGame.addParticipant(superAthlete1);
                Throwable exception = assertThrows(NoRefereeException.class, () -> testGame.gamePlay());
                String expected = String.format("No referee in game %s : %s",
                        testGame.getId(),
                        GamesHelperFunctions.firsLetterToUpper(testGame.getGameSport().toString()));
                assertEquals(expected, exception.getMessage());
            }
        }
    }

    private String idBuilder(String sportName) {
        return sportName.substring(0, 2).toUpperCase() + "01";
    }

    @Nested
    @DisplayName("addParticipant method tests")
    class addParticipantTests {

        @BeforeEach
        void configReaderSetUp(){
            when(configReaderMock.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(1);
            when(configReaderMock.getConfigInt("MAX_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(1);
        }

        @DisplayName("addParticipant of Athlete. Iterate over sports")
        @ParameterizedTest(name = "{index}. Athlete Type = ''{0}''")
        @EnumSource(AthleteType.class)
        void addParticipant_WithSportFromEnum_participationHasTheSportPerson(AthleteType _athleteType) {
            GamesAthlete participant = mock(GamesAthlete.class);
            when(participant.getAthleteType()).thenReturn(_athleteType);
            String GameSportsId = idBuilder(_athleteType.getSport().iterator().next().name());
            testGame = new OzlGame(GameSportsId);
            testGame.addParticipant(participant);

            assertEquals(testGame.getGameAthletes().get(0), participant);
        }

        @DisplayName("addParticipant official")
        @Test
        void addOfficial_gamesOfficialEqualsToMockOfficial() {
            GamesOfficial participant = mock(GamesOfficial.class);
            testGame = new OzlGame("SW01"); //swimming game, it doesn't matter
            testGame.addParticipant(participant);

            assertEquals(testGame.get_referee(), participant);
        }


        @Nested
        @DisplayName("add participant exceptions test")
        class addParticipantExceptionTests {

            @DisplayName("add participant to wrong type of game throws WrongSportException")
            @ParameterizedTest(name = "{index}. Sport Name = ''{0}''")
            @EnumSource(GameSports.class)
            void addParticipantToWrongTypeOfSport_ThrowsWrongSportException(GameSports _sport) {
                String GameSportsId = idBuilder(_sport.name());
                testGame = new OzlGame(GameSportsId);
                AthleteType _athleteType = Arrays.stream(AthleteType.values())
                        .filter(t -> t.getSport().size() == 1 && !t.getSport().iterator().next().equals(_sport))
                        .findAny()
                        .orElse(null);
                GamesAthlete participant = mock(GamesAthlete.class);
                when(participant.getAthleteType()).thenReturn(_athleteType);

                Throwable exception = assertThrows(WrongSportException.class, () -> testGame.addParticipant(participant));
                String expected = String.format("Wrong Sport Assignment. %s : %s not assigned to game %s : %s ",
                        participant.getId(),
                        participant.getName(),
                        testGame.getId(),
                        GamesHelperFunctions.firsLetterToUpper(testGame.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());
            }

            @DisplayName("add participant game full throws game full exception")
            @Test
            void addParticipantThrowsGameFullException() {
                GamesAthlete participant = mock(GamesAthlete.class);
                when(participant.getAthleteType()).thenReturn(AthleteType.swimmer);
                String GameSportsId = idBuilder(participant.getAthleteType().getSport().iterator().next().name());
                testGame = new OzlGame(GameSportsId);
                testGame.addParticipant(participant);
                GamesAthlete participant2 = mock(GamesAthlete.class);
                when(participant2.getAthleteType()).thenReturn(AthleteType.swimmer);

                Throwable exception = assertThrows(OzlGameFullException.class, () -> testGame.addParticipant(participant2));
                String expected = String.format("This game is full %s : %s",
                        testGame.getId(),
                        GamesHelperFunctions.firsLetterToUpper(testGame.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());

            }


        }

    }

    @Nested
    @DisplayName("removeParticipant method tests")
    class removeParticipantTests {

        @BeforeEach
        void setUpMock() {
            when(configReaderMock.getConfigInt("MIN_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(17);
            when(configReaderMock.getConfigInt("MAX_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(50);
            when(configReaderMock.getConfigInt("MIN_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(1);
            when(configReaderMock.getConfigInt("MAX_PARTICIPANTS", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(1);
        }

        @DisplayName("Remove Athlete particiation size 0")
        @Test
        void removeAthleteParticipationO() {
            GamesAthlete participant = spy(new GamesAthleteWithAthleteType(AthleteType.swimmer.name()));
            String GameSportsId = idBuilder(participant.getAthleteType().getSport().iterator().next().name());
            testGame = new OzlGame(GameSportsId);
            testGame.addParticipant(participant);
            testGame.removeParticipant(participant);

            assertEquals(0, testGame.getParticipation().size());
        }

        @DisplayName("Remove Athlete from game, assert participation passed as parameter has athlete")
        @Test
        void removeAthlete_participationParameterPassedContainsAthlete() {
            GamesAthlete participant = spy(new GamesAthleteWithAthleteType(AthleteType.swimmer.name()));
            ArgumentCaptor<OzlParticipation> arg = ArgumentCaptor.forClass(OzlParticipation.class);

            String GameSportsId = idBuilder(participant.getAthleteType().getSport().iterator().next().name());
            testGame = new OzlGame(GameSportsId);
            testGame.addParticipant(participant);
            testGame.removeParticipant(participant);

            verify(participant).removeParticipation(arg.capture());
            verify(participant, times(1)).removeParticipation(arg.getValue());

            assertEquals(participant, arg.getValue().gamesAthlete);
        }

        @DisplayName("Remove Referee")
        @Test
        void removeReferee_GameHasNoReferee() {
            GamesOfficial participant = mock(GamesOfficial.class);
            testGame = new OzlGame("SW01"); //swimming game, it doesn't matter
            testGame.addParticipant(participant);
            testGame.removeParticipant(participant);

            verify(participant, times(1)).removeGame();

            assertNull(testGame.get_referee());
        }


        private class GamesAthleteWithAthleteType extends GamesAthlete {
            GamesAthleteWithAthleteType(String _athleteType) {
                super(1, "Foo Bar", 25, "Victoria", _athleteType);
            }
        }


    }

    @Nested
    @DisplayName("constructor with different ID strings")
    class OzlGame_constructor {

        @ParameterizedTest(name = "{index}. Sport Name = ''{0}''")
        @EnumSource(GameSports.class)
        @DisplayName("instantiate with id prefix of exiting sports")
        void construct_withASportIdPrefix_initOk(GameSports _sport) {

            String GameSportsId = idBuilder(_sport.name());
            testGame = new OzlGame(GameSportsId);

            assertEquals(_sport, testGame.getGameSport());
        }

        @Test
        @DisplayName("contructor with ID for non-declared sport throws NoSuchElementException")
        void constructor_NonDeclaredSport_ThrowsNoSuchElementException() {
            String GameSportsId = idBuilder("Foo");

            assertThrows(NoSuchElementException.class, () -> new OzlGame(GameSportsId));

        }

    }

}