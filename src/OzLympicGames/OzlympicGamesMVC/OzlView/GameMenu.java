package OzLympicGames.OzlympicGamesMVC.OzlView;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by dimz on 1/4/17.
 */
public class GameMenu {
    private final String CONFIG_FILE = "menu.strings.properties";
    private String myContent;
    private IOzlConfigRead configReader;

    //constructor
    public GameMenu(String myContent){
        configReader = OzlConfigRead.getInstance();
        try {
            this.myContent = configReader.getConfigString(myContent, CONFIG_FILE);
        } catch (configFileMissingException err) {
            err.getMissingFile();
            System.exit(1);
        } catch (Error e) {
            System.err.println(e.toString());
        }
    }

    public String getMyContent() {
        return myContent;
    }

}
