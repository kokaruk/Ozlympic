package ozlympicgames.ozlmodel.dal;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads Config Files. Implements singleton pattern to avoid sharing violation with lazy field instantiation.
 *
 * @author dimz
 * @since 18/3/17
 */
public final class OzlConfigRead implements IOzlConfigRead {

    private static Logger logger = LogManager.getLogger();

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

    /**
     * Get int from config file. Interface implementation.
     *
     * @param myPropertyName property name string
     * @param myPropFile     property
     * @return integer from
     */
    @Override
    public int getConfigInt(String myPropertyName, String myPropFile) {
        int configInt = 0;
        try {
            configInt = Integer.parseInt(getConfigString(myPropertyName, myPropFile));
        } catch (NumberFormatException e) {
            logger.fatal(String.format("%s\r\nInvalid string to int casting format. Exiting ...", e.getMessage()));
            System.err.println();
            System.err.println(e.toString());
            System.exit(0);
        } catch (configFileMissingException err) {
            logger.fatal(err.getMessage());
            System.exit(0);
        }
        return configInt;
    }

    /**
     * Get string from config file. Interface implementation.
     *
     * @param myPropertyName property name string
     * @param myPropFile     path to property file
     * @return string of property value
     * @throws configFileMissingException if passed URI doesn't exist
     */
    @Override
    public String getConfigString(String myPropertyName, String myPropFile) throws configFileMissingException {
        Properties myProp = new Properties();

        String myPropertyString;
        try (InputStream in = getClass().getResourceAsStream(myPropFile)) {
            myProp.load(in);
            myPropertyString = myProp.getProperty(myPropertyName);
        } catch (IOException ex) {
            logger.fatal(ex.getMessage());
            throw new configFileMissingException(String.format("Unknown issue accessing config file. See if %s exist in " +
                    "OzLympicGames/OzLympicGamesMVC/ozlmodel", myPropFile));

        }
        return myPropertyString;
    }

    /**
     * @param myPropFile
     * @return
     * @throws IOException
     */
    @Override
    public Properties getProperties(String myPropFile) throws IOException {
        Properties myProp = new Properties();

        try (InputStream in = getClass().getResourceAsStream(myPropFile)) {
            myProp.load(in);
        }
        return myProp;
    }


/*
    /**
     * Write to properties file
     * @param myPropertyName property name
     * @param myPropValue value
     * @param myPropFile file name
     * @throws configFileMissingException if file is missing
     */
 /*   @Override
    public void setConfigString(String myPropertyName, String myPropValue, String myPropFile) throws IOException {
        Properties myProp = new Properties();
        try (InputStream in = new FileInputStream(myPropFile)) {
            myProp.load(in);
        }
        myProp.setProperty(myPropertyName, myPropValue);
        try (Writer w = new FileWriter(myPropFile)) {
            myProp.store(w, "");
        }
    }
    */
}