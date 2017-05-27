package ozlympicgames.ozlmodel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.dal.IOzlConfigRead;
import ozlympicgames.ozlmodel.dal.OzlConfigRead;
import ozlympicgames.ozlmodel.dal.configFileMissingException;
import ozlympicgames.ozlmodel.dal.modelPackageConfig;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Static shared functions for classes.
 * class is <b>final</b> and cannot be instantiated due to private constructor
 *
 * @author dimz
 * @since 12/3/17
 */
public final class GamesHelperFunctions {
    private static Logger logger = LogManager.getLogger();
    static private IOzlConfigRead customReader;
    private static List<String> randomNames;
    private static String REGEX_SPLIT_OPTION;

    static {
        try {
            REGEX_SPLIT_OPTION = getConfigReader().getConfigString("REGEX_SPLIT_OPTION", modelPackageConfig.MODEL_CONFIG_FILE);
        } catch (configFileMissingException e) {
            logger.fatal(e.getMessage());
            System.exit(1);
        }

        randomNames = new LinkedList<>();
    }

    // private constructor, to avoid instantiation
    private GamesHelperFunctions() {
    }

    /**
     * Method to randomly generate a double within passed set range of int
     *
     * @param min Minimum bound
     * @param max Maximum bound
     * @return random double
     */
    public static double getRandomDoubleInRange(int min, int max) {
        return min + new Random().nextDouble() * (max - min);
    }

    /**
     * Method to capitalise first letter in string
     *
     * @param myString expects an instance of string
     * @return string with capitalised first letter
     */
    public static String firsLetterToUpper(String myString) {
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

    /**
     * Factory method for properties file reader
     *
     * @return Singleton instance, implementation of  IConfigReader Interface
     * @see IOzlConfigRead
     * @see OzlConfigRead
     */
    public static IOzlConfigRead getConfigReader() {
        return customReader != null ? customReader : OzlConfigRead.getInstance();
    }

    /**
     * Method to Override instance of properties reader, used for UnitTest dependency injections
     * In reality should be removed from production code
     *
     * @param customReader expects a fake implementation of properties reader interface.
     */
    public static void setCustomReader(IOzlConfigRead customReader) {
        GamesHelperFunctions.customReader = customReader;
    }

    public static String getRandomState() {
        String[] ausssieStates = new String[]{"Australian Capital Territory", "New South Wales",
                "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory"};
        int randomArrayIndex = GamesHelperFunctions.getRandomNumberInRange(0, ausssieStates.length - 1);
        return ausssieStates[randomArrayIndex];
    }

    public static AthleteType getRandomAthleteType() {
        int randomArrayIndex = GamesHelperFunctions.getRandomNumberInRange(0, AthleteType.values().length - 1);
        return AthleteType.values()[randomArrayIndex];
    }

    public static GameSports getRandomSport() {
        int randomArrayIndex = GamesHelperFunctions.getRandomNumberInRange(0, GameSports.values().length - 1);
        return GameSports.values()[randomArrayIndex];
    }

    public static int getRandomNumberInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static int getRandomAge() {
        int minAge = getConfigReader().getConfigInt("MIN_AGE", modelPackageConfig.MODEL_CONFIG_FILE);
        int maxAge = getConfigReader().getConfigInt("MAX_AGE", modelPackageConfig.MODEL_CONFIG_FILE);
        return getRandomNumberInRange(minAge, maxAge);
    }

    public static String getRandomName() {
        //see if random names list has names, return any
        if (randomNames.size() > 0) {
            int randomArrayIndex = getRandomNumberInRange(0, randomNames.size() - 1);
            String randomName = randomNames.get(randomArrayIndex);
            randomNames.remove(randomArrayIndex); //remove name to avoid duplicates
            return randomName;
        } else {   // looks like run out of names, lets repopulate
            // read names list and recursive call to self
            try {
                randomNames = new LinkedList<>(Arrays.asList(
                        getConfigReader().getConfigString("names", modelPackageConfig.MODEL_CONFIG_FILE)
                                .split(REGEX_SPLIT_OPTION))
                );
            } catch (configFileMissingException err) {
                logger.fatal(err.getMessage());
                return null;
            }
            return getRandomName();
        }
    }
/*
    /**
     * Reads global counter if database is not present, required for id generation
     * @param counterName filename with counter
     * @return integer stored in file
     * @throws IOException
     * @throws NumberFormatException
     */
 /*   public static int readGlobalCounter(String counterName) throws IOException, NumberFormatException {
        String num = new String(Files.readAllBytes(Paths.get(counterName)));
        return Integer.parseInt(num);
    }

    /**
     * Add global counter if database not present
     * @param counterName
     * @param counter
     * @throws IOException
     */
/*    public static void setGlobalCounter(String counterName, Integer counter) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(counter.toString());
        Files.write(Paths.get(counterName), lines);
    }
*/

}
