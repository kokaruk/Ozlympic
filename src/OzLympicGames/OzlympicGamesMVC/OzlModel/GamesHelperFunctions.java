package OzLympicGames.OzlympicGamesMVC.OzlModel;

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

    // Java 8 method to randomly generate an integer within passed set range
    public static int getRandomIntInRange(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    // method to randomly generate an double within passed set range
    public static double getRandomDoubleInRange(int min, int max) {
        return min + new Random().nextDouble() * (max - min);
    }
    //method to capitalise first letter in string
    public static String firsLetterToUpper(String myString) {
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

}
