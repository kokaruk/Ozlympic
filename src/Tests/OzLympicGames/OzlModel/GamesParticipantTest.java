package OzLympicGames.OzlModel;


import OzLympicGames.GamesHelperFunctions;
import OzLympicGames.OzlGamesDAL.IOzlConfigRead;
import OzLympicGames.OzlGamesDAL.modelPackageConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author dimz
 * @since 30/4/17.
 */

@DisplayName("GameParticipant Abstract Class Testing")
class GamesParticipantTest {

    private GamesParticipant _participant;
    @Mock
    private static IOzlConfigRead configReaderMock;

    @BeforeAll
    static void setUp() {
        configReaderMock = mock(IOzlConfigRead.class);
        // stubbing
        when(configReaderMock.getConfigInt("MIN_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(17);
        when(configReaderMock.getConfigInt("MAX_AGE", modelPackageConfig.MODEL_CONFIG_FILE)).thenReturn(50);
        // insert into seam of config reader
        GamesHelperFunctions.setCustomReader(configReaderMock);
    }


    @Test
    @DisplayName("JUnit 5 practice: disabled test. Never Mind")
    @Disabled("for demonstration purposes")
    void skippedTest() {
        // not executed
    }

    @Nested
    @DisplayName("class constructor tests")
    class constructorTests{
        @Nested
        @DisplayName("Class constructor exceptions test")
        class constructorThrows{
            @ParameterizedTest(name = "{index}. State Name = ''{0}''")
            @ValueSource(strings = { "Texas", "California", "Alaska" })
            @DisplayName("GamesParticipant Constructor Throws IllIllegalAustralianStateException")
            void GamesParticipant_Constructor_ThrowsIllIllegalAustralianStateException(String argument) {

                assertThrows(IllegalAustralianStateException.class, () -> new ConcreteGamesParticipantClass(argument));
            }

            @ParameterizedTest(name = "{index}. State Name = ''{0}''")
            @ValueSource(strings = { "Texas", "California", "Alaska" })
            @DisplayName("GamesParticipant Constructor Throws Exception message Unknown Au State: + %Param%")
            void GamesParticipant_Constructor_ThrowsIllIllegalAustralianStateExceptionMessage(String argument) {
                Throwable exception  = assertThrows(IllegalAustralianStateException.class, () -> new ConcreteGamesParticipantClass(argument));

                assertEquals("Unknown Au State: " + argument, exception.getMessage());
            }
        }


        @ParameterizedTest(name = "{index}. State Name = ''{0}''")
        @ValueSource(strings = { "Australian Capital Territory", "New South Wales",
                "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory" })
        @DisplayName("GamesParticipant Instantiate Return State = %Param%")
        void GamesParticipant_Constructor_returnsState(String argument) {
            _participant =  new ConcreteGamesParticipantClass(argument);

            assertEquals(argument, _participant.getState());
        }
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
       assertEquals(argument, (int)participant.getAge());
    }

    private class ConcreteGamesParticipantClass extends GamesParticipant {
        ConcreteGamesParticipantClass(String _state){
            super("ID", "Alex Foo", 25, _state);
        }
        ConcreteGamesParticipantClass(int _age){
            super("ID", "Alex Foo", _age, "Victoria");
        }
    }

    @AfterEach
    void resetParticipant() {
        _participant = null;
    }

    @AfterAll
    static void tearDown(){
        GamesHelperFunctions.setCustomReader(null);
    }

}