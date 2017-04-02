package OzLympicGames.OzlympicGamesMVC.OzlView;

import java.util.Arrays;
import java.util.List;


/**
 * Created by dimi on 11/3/17.
 * Strings for view controller
 */
public class GameView {

    private GameMenu currentMenu;

    // constructor
    public GameView() {
    }

    public void setCurrentMenu(GameMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    // no variables
    public void updateScreen() {
        String menuContentString = currentMenu.getMyContent();
        displayCurrentMenu(menuContentString);
    }

    // one variable format
    public void updateScreen(String modelMessage) {
        String menuContentString = String.format(currentMenu.getMyContent(), modelMessage);
        displayCurrentMenu(menuContentString);
    }

    private void displayCurrentMenu(String menuContentString) {
        clearScreen();
        List<String> menuContent = Arrays.asList(menuContentString.split(","));
        for (String menuLine : menuContent) {
            System.out.println("\033[0m" + menuLine);
        }
    }

    // method to clear terminal screen
    public void clearScreen() {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }
}