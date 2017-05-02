package OzLympicGames.OzlympicGamesMVC.OzlModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dimz
 * @since 1/5/17.
 */
@DisplayName("GamesAthlete Class Test")
class GamesAthleteTest {

    private GamesAthlete _athlete;
    private OzlGame _game;
    private OzlParticipation _participation;

    @BeforeEach
    void setUp(){
        _athlete = new GamesAthleteWithAthleteType(AthleteType.superAthlete);
        _game = mock(OzlGame.class);
        _participation = new OzlParticipation(_athlete, _game);
        _athlete.addParticipation(_participation);
    }

    @Nested
    @DisplayName("Method: removeParticipation")
    class removeParticipation{
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
    class compete{

        @ParameterizedTest(name = "{index}. Sport Name = ''{0}''")
        @EnumSource(GameSports.class)
        @DisplayName("compete: Make SuperAthlete compete in all Sports Returns double in range of sport")
        void compete_makeAthleteCompeteInAllSports_returnsDoubleInRange(GameSports _sport) {
            when(_game.getGameSport()).thenReturn(_sport);
            _athlete.compete(_participation);

            assertTrue(_athlete.getLastGameCompeteTime() >= _sport.getMin() && _athlete.getLastGameCompeteTime() <= _sport.getMax());
        }

        @Nested
        @DisplayName("compete exception tests")
        class competeThrows{
            @Test
            @DisplayName("compete participation is not present throws  IllegalGameException")
            void compete_participationIsNotPresent_throwsIllegalGameException() {
                when(_game.getGameSport()).thenReturn(GameSports.cycling);
                when(_game.getId()).thenReturn("CY01");
                OzlParticipation _participation2 = new OzlParticipation(_athlete, _game);
                assertThrows(IllegalGameException.class, () -> _athlete.compete(_participation2) );
            }

            @Test
            @DisplayName("compete participation not set, error message ID : Name not assigned to game ID : GameSport ")
            void compete_participationIsNotPresent_throwsIllegalGameExceptionMessage() {
                when(_game.getGameSport()).thenReturn(GameSports.cycling);
                when(_game.getId()).thenReturn("CY01");
                OzlParticipation _participation2 = new OzlParticipation(_athlete, _game);
                Throwable exception = assertThrows(IllegalGameException.class, () -> _athlete.compete(_participation2) );
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
    @DisplayName("getGamesCore method tests")
    class getGameScoreTests{

        @Test
        @DisplayName("Get Game Score, always 0")
        void getGameScore_athleteNeverAssignedPoints_ExpectInt0(){
            when(_game.getGameSport()).thenReturn(GameSports.cycling);
            when(_game.getId()).thenReturn("CY01");
            _athlete.compete(_participation);

            assertEquals(0, (int)_athlete.getGameScore(_game));
        }

        @Nested
        @DisplayName("getGameScore exception tests")
        class getGameScoreThrows{

        }

    }


    @Test
    void getGameTime() {
    }

    private class GamesAthleteWithAthleteType extends GamesAthlete {
        GamesAthleteWithAthleteType(AthleteType _athleteType){
            super(GamesAthlete.idPrefix(_athleteType)+"01", "Foo Bar", 25, "Victoria", _athleteType );
        }
    }
}