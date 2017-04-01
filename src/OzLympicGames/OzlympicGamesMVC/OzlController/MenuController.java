package OzLympicGames.OzlympicGamesMVC.OzlController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by dimz on 2/4/17.
 */
public abstract class MenuController {

    private Map<Integer, MenuController> subMenuControllers;
    private MenuController parentMenuController;
    public void setParentMenuController(MenuController parentMenuController) {
        this.parentMenuController = parentMenuController;
    }

    public Map<Integer, MenuController> getSubMenuControllers() {
        return subMenuControllers;
    }
    // no arguments constructor
    MenuController(){};
    // constructor
    MenuController(HashMap<Integer, MenuController> subMenuControllers){
        this.subMenuControllers = subMenuControllers;
    }

    abstract void updateViewMenu();
    abstract void updateView();
    abstract void takeControl();


    // scan for user input, call recursively, unit received an integer.
    int getUserIntInput() {
        System.out.println("");
        System.out.println("");
        System.out.print("\033[32mMake a choice: "); //green
        Scanner scanner = new Scanner(System.in);
        String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

        int menuSelection;

        try {
            menuSelection = Integer.parseInt (menuSelectionString);
        } catch (NumberFormatException e) {
            System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
            return getUserIntInput();
        }

        if (menuSelection > subMenuControllers.size() || menuSelection < 1 ){
            System.out.println("\033[31mNo such option."); //red
            return getUserIntInput();
        } else {
            return menuSelection;
        }
    }

}
