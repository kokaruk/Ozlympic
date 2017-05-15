package OzLympicGames;

import OzLympicGames.OzlGamesDAL.IOzlConfigRead;
import OzLympicGames.OzlGamesDAL.OzlConfigRead;

import java.util.Random;

/**
 * Static shared functions for classes.
 * class is <b>final</b> and cannot be instantiated due to private constructor
 *
 * @author dimz
 * @since 12/3/17
 */
public final class GamesHelperFunctions {

    static private IOzlConfigRead customReader;

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

    /**
     * Build string of ? for sql, list of wildcards
     * @param wildCards count of wildcards for sql string
     * @return string of comma separated '?'
     */
    public static String buildWildCards(int wildCards){
        StringBuilder w = new StringBuilder();
        for (int i = 0; i<wildCards; i++){
            w.append( i == 0 ? "?" : ",?");
        }
        return w.toString();
    }

    public static String selectColumnWildCardBuilder(String columns){
        String[] columnsArray = columns.split(",[ ]*");
        StringBuilder c = new StringBuilder();
        for (int i = 0; i < columnsArray.length; i++){
            c.append(i==0 ? columnsArray[i] + "=? " : "AND " + columnsArray[i] + "=? ");
        }
        return c.toString();
    }

}
