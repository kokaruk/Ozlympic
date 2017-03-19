package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dimi on 18/3/17.
 * Implementing Singleton design Patter as Object.GetClass can't be instantiated
 */


final class OzlConfigRead implements IOzlConfigRead {

    // private constructor
    private OzlConfigRead(){}

    // singleton instance
    private static OzlConfigRead instance;
    // lazy instantiation
    static OzlConfigRead getInstance(){
        if(instance == null){
            instance = new OzlConfigRead();
        }
        return instance;
    }

     // returns integer from config file
    @Override
    public int getConfigInt(String myPropertyName){
            Properties myProp = new Properties();
            InputStream in = getClass().getResourceAsStream("config.properties");
            int configInt=0;
            try {
                myProp.load(in);
                in.close();
                configInt = Integer.parseInt (
                        myProp.getProperty(myPropertyName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return configInt;
        }

    // returns string from config file
    @Override
    public String getConfigString(String myPropertyName){
        Properties myProp = new Properties();
        InputStream in = getClass().getResourceAsStream("config.properties");
        String myPropertyString = "";
        try {
            myProp.load(in);
            in.close();
            myPropertyString = myProp.getProperty(myPropertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return myPropertyString;
    }
}