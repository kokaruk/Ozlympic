package OzLympicGames.OzlympicGamesMVC.OzlView;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by dimi on 11/3/17.
 * Strings for view controller
 *
 */
public class GameView {

    private final IMenu currentMenu;

    public IMenu getCurrentMenu() {
        return currentMenu;
    }

    // constructor
    public GameView(){
        currentMenu = new mainMenu();
    }

    //
    public void updateScreen(String modelMessage) {
        clearScreen();
        displayCurrentMenu(modelMessage);
    }

    private void displayCurrentMenu(String modelMessage){
        String menuContentString = String.format(currentMenu.getMyContent(), modelMessage);
        List<String> menuContent =  Arrays.asList(menuContentString.split(","));
        for (String menuLine : menuContent) {
            System.out.println(menuLine);
        }
    }

    // method to clear terminal screen
    public void clearScreen() {
        final String ANSI_CLS = "\u001b[2J";
        final String ANSI_HOME = "\u001b[H";
        System.out.print(ANSI_CLS + ANSI_HOME);
        System.out.flush();
    }

    private class mainMenu extends gameMenu {

        private final Map<Integer, IMenu> mySubmenus = new ConcurrentHashMap<>();

        mainMenu(){
            super("mainMenu");
            mySubmenus.put(1, new submenu() );
            mySubmenus.put(2, new submenu() );
            mySubmenus.put(3, new submenu() );
            mySubmenus.put(4, new submenu() );
            mySubmenus.put(5, new submenu() );
            mySubmenus.put(6, new submenu() );
        }

        @Override
        public Map<Integer, IMenu> getMySubmenus() {
            return mySubmenus;
        }
    }

    private class submenu extends gameMenu {

        private final Map<Integer, IMenu> mySubmenus = new ConcurrentHashMap<>();

        submenu(){
            super("some");

        }

        @Override
        public Map<Integer, IMenu> getMySubmenus() {
            return mySubmenus;
        }
    }

}
