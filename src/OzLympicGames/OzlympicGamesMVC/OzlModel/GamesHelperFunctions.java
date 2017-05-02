package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlModel.GamesAthlete;
import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGame;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 12/3/17.
 * Static shared functions for classes
 * class is final and cannot be instantiated
 */
public final class GamesHelperFunctions {

    // private constructor, to avoid instantiation
    private GamesHelperFunctions() {
    }

    static private IOzlConfigRead customReader;

    // Java 8 method to randomly generate an integer within passed set range
    public static int getRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // method to randomly generate an double within passed set range
    static double getRandomDoubleInRange(int min, int max) {
        return min + new Random().nextDouble() * (max - min);
    }
    //method to capitalise first letter in string
    static String firsLetterToUpper(String myString) {
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

    // protected factory method for config reader, can be extracted and overridden in tests
    static IOzlConfigRead getConfigReader() {
        return customReader != null ? customReader : OzlConfigRead.getInstance();
    }

    static void setCustomReader(IOzlConfigRead customReader) {
        GamesHelperFunctions.customReader = customReader;
    }
}
