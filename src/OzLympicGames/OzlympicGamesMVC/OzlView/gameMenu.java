package OzLympicGames.OzlympicGamesMVC.OzlView;

import OzLympicGames.OzlympicGamesMVC.OzlGamesData.*;

/**
 * Created by dimz on 1/4/17.
 */
abstract class gameMenu implements IMenu {
    private String myContent;
    private IOzlConfigRead configReader;

    public gameMenu(String myContent){
        configReader = OzlConfigRead.getInstance();
        try {
            this.myContent = configReader.getConfigString(myContent, viewPackageConfig.viewConfigFile);
        } catch (configFileMissingException err) {
            err.getMissingFile();
            System.exit(1);
        } catch (Error e) {
            System.err.println(e.toString());
        }
    }

    @Override
    public String getMyContent() {
        return myContent;
    }

}
