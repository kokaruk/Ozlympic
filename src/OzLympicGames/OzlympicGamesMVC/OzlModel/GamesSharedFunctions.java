package OzLympicGames.OzlympicGamesMVC.OzlModel;
import java.util.*;

/**
 *
 * Created by dimi on 12/3/17.
 * Static shared functions for classes
 * class is final and cannot be instantiated
 */
final class GamesSharedFunctions {
    // private constructor
    private GamesSharedFunctions() {}

    // method to randomly generate an integer within passed set range
    static int getRandomNumberInRange(int min, int max) {
        Random myRandomNumber = new Random();
        return myRandomNumber.ints(min, (max + 1)).findFirst().getAsInt();
    }

    //method to capitalise first letter in string
    static String firsLetterToUpper(String myString){
        return Character.toUpperCase(myString.charAt(0)) + myString.substring(1);
    }

}
