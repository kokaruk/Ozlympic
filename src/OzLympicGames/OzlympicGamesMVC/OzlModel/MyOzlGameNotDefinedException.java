package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 14/3/17.
 * Exception thrown by Participants if Game for participant is not set
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
