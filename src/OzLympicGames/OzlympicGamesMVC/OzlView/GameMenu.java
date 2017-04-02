package OzLympicGames.OzlympicGamesMVC.OzlView;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.configFileMissingException;


/**
 * Created by dimz on 1/4/17.
 * Text Interface, displays strings, from menu class.
 */
public class GameMenu {
    private String myContent;
    private int backOptionCount = 0;

    //constructor
    public GameMenu(String myContent) {
        IOzlConfigRead configReader = OzlConfigRead.getInstance();
        try {
            String CONFIG_FILE = "menu.strings.properties";
            this.myContent = configReader.getConfigString(myContent, CONFIG_FILE);
        } catch (configFileMissingException err) {
            err.getMissingFile();
            System.exit(1);
        } catch (Error e) {
            System.err.println(e.toString());
        }
    }

    // constructor with back option enabled
    public GameMenu(String myContent, int backOptionCount) {
        this(myContent);
        this.backOptionCount = backOptionCount;
    }

    // constructor with programmatic generated menu options
    public GameMenu(int backOptionCount, String generatedOptions) {
        this.backOptionCount = backOptionCount;
        this.myContent = generatedOptions;
    }

    // back option present, append back to previous menu option
    public String getMyContent() {
        return backOptionCount > 0 ? String.format("%s, \r\n%d. Back to previous menu", myContent, backOptionCount) : myContent;
    }

}
