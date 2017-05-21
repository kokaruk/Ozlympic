package ozlympicgames.ozlmodel.dal;

import java.io.IOException;
import java.util.Properties;

/**
 * Interface for Config file Reader.
 * @author dimz
 * @since 19/3/17
 */
public interface IOzlConfigRead {
    /**
     * Get int from config file.
     * @param myPropertyName property name string
     * @param myPropFile path to property file
     * @return parsed integer
     */
    int getConfigInt(String myPropertyName, String myPropFile);

    /**
     * Get string from config file.
     * @param myPropertyName property name string
     * @param myPropFile path to property file
     * @return string of property value
     * @throws configFileMissingException if passed URI doesn't exist
     */
    String getConfigString(String myPropertyName, String myPropFile) throws configFileMissingException;

    /**
     *
     * @param myPropFile
     * @return
     * @throws IOException
     */
    Properties getProperties(String myPropFile) throws IOException;

    /*/**
     * Write to properties file
     * @param myPropertyName property name
     * @param myPropValue value
     * @param myPropFile file name
     * @throws configFileMissingException if file is missing
     */ /*
    void setConfigString(String myPropertyName, String myPropValue, String myPropFile) throws IOException;
    */
}
