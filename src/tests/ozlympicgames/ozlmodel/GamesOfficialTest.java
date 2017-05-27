package ozlympicgames.ozlmodel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author dimz
 * @since 5/5/17.
 */

class GamesOfficialTest {

    List<GamesAthlete> listOfWinners;
    @Mock
    private OzlGame _game;
    private GamesOfficial officialUnderTest;
    private Set<OzlParticipation> participationSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(_game.getGameSport()).thenReturn(GameSports.cycling);
        participationSet = new HashSet<>();
        officialUnderTest = new GamesOfficial(5, "Foo Bar", 32, "Victoria");
        officialUnderTest.setGame(_game);
        listOfWinners = listOfWinners(participationSet);

        when(_game.getGameAthletesWinnersSortedByTime()).thenReturn(listOfWinners);
        when(_game.getParticipation()).thenReturn(participationSet);
    }

    private List<GamesAthlete> listOfWinners(Set<OzlParticipation> participationSet) {

        // mock list of winners
        List<GamesAthlete> winners = new ArrayList<GamesAthlete>() {{
            add(getAthlete(1));
            add(getAthlete(2));
            add(getAthlete(3));
        }};

        // make mock list athletes with participation assignment and set time for each assignment
        double minTime = _game.getGameSport().getMin();
        for (GamesAthlete champion : winners) {
            OzlParticipation champGameParticipation = new OzlParticipation(champion, _game);
            champion.addParticipation(champGameParticipation);
            participationSet.add(champGameParticipation);
            //increase time for each iteration)
            champGameParticipation.result = minTime += 10;
        }
        return winners;
    }

    private GamesAthlete getAthlete(Integer _id) {
        return new GamesAthlete(_id, "Foo Bar", 25, "Victoria", AthleteType.cyclist.name());
    }

    @Nested
    @DisplayName("Method: awardPoints")
    class awardPointsTest {

        @Test
        @DisplayName("awardPoints To winners Sum Of Total Points = 9")
        void awardPoints_awardPointToWinners_sumOfTotalPoints9() {
            when(_game.isGamePlayed()).thenReturn(true);
            officialUnderTest.awardPoints(_game);
            int totalPoints = 0;
            for (OzlParticipation _participation : _game.getParticipation()) {
                totalPoints += _participation.getScore();
            }
            assertEquals(9, totalPoints);
        }

        @Test
        @DisplayName("awardPoints to Winners. verify call to getter gameIsPlayed with mockito")
        void awardPoints_awardPointToWinners_verifyGameisPlayedGetterCall() {
            when(_game.isGamePlayed()).thenReturn(true);
            officialUnderTest.awardPoints(_game);
            verify(_game, times(1)).isGamePlayed();
        }

        @Nested
        @DisplayName("Exceptions Test")
        class awardPointsExceptionsTest {

            @Test
            @DisplayName("award points _game=null throws illegal_game exception")
            void awardsPoints_gameIsNull_throwsIllegalGameException() {
                officialUnderTest.setGame(null);

                assertThrows(IllegalGameException.class, () -> officialUnderTest.awardPoints(_game));
            }

            @Test
            @DisplayName("awardPonts game is different throws IllegalGameException")
            void awardPoints_assignedToDifferentGame_throwsIllegalGameException() {
                officialUnderTest.setGame(mock(OzlGame.class));

                assertThrows(IllegalGameException.class, () -> officialUnderTest.awardPoints(_game));
            }

            @Test
            @DisplayName("awardPonts game is different throws IllegalGameException Message")
            void awardPoints_assignedToDifferentGame_throwsIllegalGameExceptionMessage() {
                officialUnderTest.setGame(mock(OzlGame.class));
                Throwable exception = assertThrows(IllegalGameException.class, () -> officialUnderTest.awardPoints(_game));
                String expected = String.format("%s : %s not assigned to game %s : %s ",
                        officialUnderTest.getId(),
                        officialUnderTest.getName(),
                        _game.getId(),
                        GamesHelperFunctions.firsLetterToUpper(_game.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());
            }

            @Test
            @DisplayName("awardPoints_newGame_ThrowsGameNeverPlayedException")
            void awardPoints_athleteWrongGame_ThrowsGameNeverPlayedException() {
                assertThrows(GameNeverPlayedException.class, () -> officialUnderTest.awardPoints(_game));
            }

            @Test
            @DisplayName("awardPoints_newGame_ThrowsGameNeverPlayedException Message")
            void awardPoints_athleteWrongGame_ThrowsGameNeverPlayedExceptionMessage() {
                Throwable exception = assertThrows(GameNeverPlayedException.class, () -> officialUnderTest.awardPoints(_game));
                String expected = String.format("This game never run %s : %s",
                        _game.getId(),
                        GamesHelperFunctions.firsLetterToUpper(_game.getGameSport().toString()));

                assertEquals(expected, exception.getMessage());
            }

            @Test
            @DisplayName("awardPoints one of athletes not assigned to the game")
            void awardPoints_athleteWrongGame_ThrowsIllegalGameException() {
                OzlParticipation participation = participationSet.stream().findAny().get();
                participation.game = mock(OzlGame.class);
                when(_game.isGamePlayed()).thenReturn(true);

                assertThrows(IllegalGameException.class, () -> officialUnderTest.awardPoints(_game));
            }
        }
    }

}