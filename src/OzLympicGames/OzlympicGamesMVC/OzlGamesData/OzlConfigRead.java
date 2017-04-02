package OzLympicGames.OzlympicGamesMVC.OzlGamesData;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by dimi on 18/3/17.
 * Implementing Singleton design Patter as Object.GetClass can't be instantiated
 */
public final class OzlConfigRead implements IOzlConfigRead {

    // singleton instance
    private static OzlConfigRead instance;

    // private constructor
    private OzlConfigRead() {
    }

    // lazy instantiation
    public static OzlConfigRead getInstance() {
        if (instance == null) {
            instance = new OzlConfigRead();
        }
        return instance;
    }

    // returns integer from config file
    @Override
    public int getConfigInt(String myPropertyName, String myPropFile) {
        int configInt = 0;
        try {
            configInt = Integer.parseInt(getConfigString(myPropertyName, myPropFile));
        } catch (NumberFormatException e) {
            System.err.println("Invalid string to int casting format. Exiting ...");
            System.err.println(e.toString());
        } catch (configFileMissingException err) {
            err.getMissingFile();
            System.exit(1);
        }
        return configInt;
    }

    // returns string from config file
    @Override
    public String getConfigString(String myPropertyName, String myPropFile) throws configFileMissingException {
        Properties myProp = new Properties();
        InputStream in = getClass().getResourceAsStream(myPropFile);
        String myPropertyString = "";
        try {
            myProp.load(in);
            in.close();
            myPropertyString = myProp.getProperty(myPropertyName);
        } catch (IOException ex) {
            System.err.println(
                    String.format("Unknown issue accessing config file. See if %s exist in " +
                            "OzLympicGames/OzLympicGamesMVC/OzlModel", myPropFile)
            );
            ex.printStackTrace();
        } catch (Exception E) {
            throw new configFileMissingException(myPropFile);
        }

        return myPropertyString;
    }
}