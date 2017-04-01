package OzLympicGames.OzlympicGamesMVC.OzlController;

import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGamesModel;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameView;

import java.util.Scanner;

/**
 * Created by dimi on 11/3/17.
 * General Game Controller Class
 */


public class GameController {
    private final GameView view;
    private OzlGamesModel model1;

    public GameController(OzlGamesModel model, GameView view) {
        this.model1 = model;
        this.view = view;
    }

    public void startGames()  {
        updateView(mainMenuMessage());
        userIntInput();
    }

    private void updateView(String message) {
        view.updateScreen(message);
    }

    private String mainMenuMessage(){
        return String.format("Games recorded: %d | Athletes: %d ",
                model1.getMyOzlGames().size(),
                model1.getmYgamesAthletes().size());
    }

    // scan for user input, call recursively, unit received an integer.
    private int userIntInput() {
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
            return userIntInput();
        }

        if (menuSelection > view.getCurrentMenu().getMySubmenus().size()){
            System.out.println("\033[31mNo such option."); //red
            return userIntInput();
        } else {
            return menuSelection;
        }
    }
}
