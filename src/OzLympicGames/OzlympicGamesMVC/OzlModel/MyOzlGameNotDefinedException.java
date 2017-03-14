package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 14/3/17.
 */
class MyOzlGameNotDefinedException extends Exception{
    // Parameter-less Constructor
    MyOzlGameNotDefinedException() {}

    // Constructor with a message
    MyOzlGameNotDefinedException(String message)
    {
        super(message);
    }
}
