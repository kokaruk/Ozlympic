package ozlympicgames.ozlmodel;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author dimz
 * @since 1/5/17.
 */
@DisplayName("GamesAthlete Class Test")
class GamesAthleteTest {
    @Mock
    private OzlGame _game;
    private GamesAthlete _athlete;
    private OzlParticipation _participation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        _athlete = new GamesAthleteWithAthleteType(AthleteType.superAthlete.name());
        when(_game.getGameSport()).thenReturn(GameSports.cycling);
        when(_game.getId()).thenReturn("CY01");
        _participation = new OzlParticipation(_athlete, _game);
        _athlete.addParticipation(_participation);
    }

    @Nested
    @DisplayName("Method: removeParticipation")
    class removeParticipation {
        @Test
        @DisplayName("removeParticipation Athlete Has This Participation Participation Reduced")
        void removeParticipation_AthleteHasThisParticipation_ParticipationReduced() {
            _athlete.removeParticipation(_participation);

            assertEquals(0, _athlete.getParticipation().size());
        }

        @Test
        @DisplayName("removeParticipation AthleteHasAParticipation but not this one Participation Same")
        void removeParticipation_AthleteHasAParticipation_ParticipationReduced() {
            OzlParticipation _participation2 = new OzlParticipation(_athlete, mock(OzlGame.class));
            _athlete.removeParticipation(_participation2);

            assertEquals(1, _athlete.getParticipation().size());
        }
    }


    @Nested
    @DisplayName("Method: compete")
    class compete {

        @BeforeEach
        void athleteCompete() {
            _athlete.compete(_participation);
        }

        @ParameterizedTest(name = "{index}. Sport Name = ''{0}''")
        @EnumSource(GameSports.class)
        @DisplayName("compete: Make SuperAthlete compete in all Sports Returns double in range of sport")
        void compete_makeAthleteCompeteInAllSports_returnsDoubleInRange(GameSports _sport) {
            when(_game.getGameSport()).thenReturn(_sport);
            _athlete.compete(_participation);

            assertTrue(_athlete.getLastGameCompeteTime() >= _sport.getMin()
                    && _athlete.getLastGameCompeteTime() <= _sport.getMax());
        }

        @Nested
        @DisplayName("compete exception tests")
        class competeThrows {

            private OzlParticipation _participation2;

            @BeforeEach
            void initParticipation2() {
                _participation2 = new OzlParticipation(_athlete, _game);
            }


            @Test
            @DisplayName("compete participation is not present throws  IllegalGameException")
            void compete_participationIsNotPresent_throwsIllegalGameException() {

                assertThrows(IllegalGameException.class, () -> _athlete.compete(_participation2));
            }

            @Test
            @DisplayName("compete participation not set, error message ID : Name not assigned to game ID : GameSport ")
            void compete_participationIsNotPresent_throwsIllegalGameExceptionMessage() {

                Throwable exception = assertThrows(IllegalGameException.class, () -> _athlete.compete(_participation2));
                String expected = String.format("%s : %s not assigned to game %s : %s ",
                        _athlete.getId(),
                        _athlete.getName(),
                        _game.getId(),
                        GamesHelperFunctions.firsLetterToUpper(_game.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("'getGamesCore' method tests")
    class getGameScoreTests {

        @BeforeEach
        void athleteCompete() {
            _athlete.compete(_participation);
        }

        @Test
        @DisplayName("athlete never played or haven't scored points. Returns 0")
        void getGameScore_athleteNeverPlayedOrHAsNoPoints_ExpectInt0() {

            assertEquals(0, (int) _athlete.getGameScore(_game));
        }

        @RepeatedTest(value = 10, name = "{displayName} {currentRepetition}/{totalRepetitions}")
        @DisplayName("GetGameScore' repeated test from 1 to 10 asserts total points equals to repetition")
        void getGameScore_setParticipationScore_athleteGetsNumber(RepetitionInfo repetitionInfo) {
            _participation.score = repetitionInfo.getCurrentRepetition();

            assertEquals(repetitionInfo.getCurrentRepetition(), (int) _athlete.getGameScore(_game));
        }

        @Nested
        @DisplayName("getGameScore exception tests")
        class getGameScoreThrows {

            @Mock
            private OzlGame _game2;

            @BeforeEach
            void initParticipation2() {
                MockitoAnnotations.initMocks(this);
                when(_game2.getGameSport()).thenReturn(GameSports.cycling);
                when(_game2.getId()).thenReturn("CY02");
            }

            @Test
            @DisplayName("GameScore required game not assigned. Throws IllegalGameException")
            void getGameScore_athleteNoAssignedToTheGame_ThrowsIllegalGameException() {

                assertThrows(IllegalGameException.class, () -> _athlete.getGameScore(_game2));
            }

            @Test
            @DisplayName("GameScore required game not assigned. Throws IllegalGameException Message check")
            void getGameScore_athleteNoAssignedToTheGame_ThrowsIllegalGameExceptionMessage() {

                Throwable exception = assertThrows(IllegalGameException.class, () -> _athlete.getGameScore(_game2));
                String expected = String.format("%s : %s not assigned to game %s : %s ",
                        _athlete.getId(),
                        _athlete.getName(),
                        _game2.getId(),
                        GamesHelperFunctions.firsLetterToUpper(_game.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());
            }

        }

    }

    @Nested
    @DisplayName("'GetGameTime' exception tests")
    class getGameTimeThrows {

        @Mock
        private OzlGame _game2;

        @BeforeEach
        void initParticipation2() {
            _athlete.compete(_participation);
            MockitoAnnotations.initMocks(this);
            when(_game2.getGameSport()).thenReturn(GameSports.cycling);
            when(_game2.getId()).thenReturn("CY02");
        }

        @Test
        @DisplayName("'GetGameTime' required game not assigned. Throws IllegalGameException")
        void getGameScore_athleteNoAssignedToTheGame_ThrowsIllegalGameException() {

            assertThrows(IllegalGameException.class, () -> _athlete.getGameTime(_game2));
        }

        @Test
        @DisplayName("'GetGameTime' required game not assigned. Throws IllegalGameException assert Message")
        void getGameScore_athleteNoAssignedToTheGame_ThrowsIllegalGameExceptionMessage() {

            Throwable exception = assertThrows(IllegalGameException.class, () -> _athlete.getGameTime(_game2));
            String expected = String.format("%s : %s not assigned to game %s : %s ",
                    _athlete.getId(),
                    _athlete.getName(),
                    _game2.getId(),
                    GamesHelperFunctions.firsLetterToUpper(_game.getGameSport().toString()));

            assertEquals(expected, exception.getMessage());
        }
    }


    private class GamesAthleteWithAthleteType extends GamesAthlete {
        GamesAthleteWithAthleteType(String _athleteType) {
            super(1, "Foo Bar", 25, "Victoria", _athleteType);
        }
    }
}