package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.*;

/**
 *
 * Created by dimi on 12/3/17.
 * Static shared functions for classes
 * class is final and cannot be instantiated
 */
final class GamesHelperFunctions {

    // private constructor
    private GamesHelperFunctions() {}

    // Java 8 method to randomly generate an integer within passed set range
    static int getRandomNumberInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // method to randomly generate an double within passed set range
    static double getRandomDoubleInRange(int min, int max) {
        return min + new Random().nextDouble() * (max - min);
    }

    //method to capitalise first letter in string
    static String firsLetterToUpper(String myString){
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

}
