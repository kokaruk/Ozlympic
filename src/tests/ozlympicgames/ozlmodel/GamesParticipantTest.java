package ozlympicgames.ozlmodel;


import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import ozlympicgames.ozlmodel.dal.IOzlConfigRead;
import ozlympicgames.ozlmodel.dal.modelPackageConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author dimz
 * @since 30/4/17.
 */

@DisplayName("GameParticipant Abstract Class Testing")
class GamesParticipantTest {

    @Mock
    private static IOzlConfigRead configReaderMock;
    private GamesParticipant _participant;

    @BeforeAll
    static void setUp() {
        configReaderMock = mock(IOzlConfigRead.class);
        // stubbing
        when(configReaderMock.getConfigInt("MIN_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(17);
        when(configReaderMock.getConfigInt("MAX_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(50);
        // insert into seam of config reader
        GamesHelperFunctions.setCustomReader(configReaderMock);
    }

    @AfterAll
    static void tearDown() {
        GamesHelperFunctions.setCustomReader(null);
    }

    @Test
    @DisplayName("JUnit 5 practice: disabled test. Never Mind")
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @ParameterizedTest(name = "{index}. Age = ''{0}''")
    @ValueSource(ints = {10, 16, 51, 58})
    @DisplayName("GamesParticipant AgeOutOfBounds TrowsIllegalAgeException with default values")
    void GamesParticipant_AgeOutOfBounds_TrowsIllegalAgeException(int argument) {
        assertThrows(IllegalAgeException.class, () -> new ConcreteGamesParticipantClass(argument));
    }

    @ParameterizedTest(name = "{index}. Age = ''{0}''")
    @ValueSource(ints = {17, 22, 50, 34})
    @DisplayName("GamesParticipant Age Within bounds Creates Instance of participant class")
    void GamesParticipant_AgeInBounds_CreatesInstance(int argument) {
        GamesParticipant participant = new ConcreteGamesParticipantClass(argument);
        assertEquals(argument, (int) participant.getAge());
    }

    @AfterEach
    void resetParticipant() {
        _participant = null;
    }

    @Nested
    @DisplayName("class constructor tests")
    class constructorTests {
        @ParameterizedTest(name = "{index}. State Name = ''{0}''")
        @ValueSource(strings = {"Australian Capital Territory", "New South Wales",
                "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory"})
        @DisplayName("GamesParticipant Instantiate Return State = %Param%")
        void GamesParticipant_Constructor_returnsState(String argument) {
            _participant = new ConcreteGamesParticipantClass(argument);

            assertEquals(argument, _participant.getState());
        }

        @Nested
        @DisplayName("Class constructor exceptions test")
        class constructorThrows {
            @ParameterizedTest(name = "{index}. State Name = ''{0}''")
            @ValueSource(strings = {"Texas", "California", "Alaska"})
            @DisplayName("GamesParticipant Constructor Throws IllIllegalAustralianStateException")
            void GamesParticipant_Constructor_ThrowsIllIllegalAustralianStateException(String argument) {

                assertThrows(IllegalAustralianStateException.class, () -> new ConcreteGamesParticipantClass(argument));
            }

            @ParameterizedTest(name = "{index}. State Name = ''{0}''")
            @ValueSource(strings = {"Texas", "California", "Alaska"})
            @DisplayName("GamesParticipant Constructor Throws Exception message Unknown Au State: + %Param%")
            void GamesParticipant_Constructor_ThrowsIllIllegalAustralianStateExceptionMessage(String argument) {
                Throwable exception = assertThrows(IllegalAustralianStateException.class, () -> new ConcreteGamesParticipantClass(argument));

                assertEquals("Unknown Au State: " + argument, exception.getMessage());
            }
        }
    }

    private class ConcreteGamesParticipantClass extends GamesParticipant {

        ConcreteGamesParticipantClass(String _state) {
            super(1, "Alex Foo", 25, _state);
        }

        ConcreteGamesParticipantClass(int _age) {
            super(1, "Alex Foo", _age, "Victoria");
        }

        @Override
        public String getId() {
            return super.get_id().toString();
        }
    }

}