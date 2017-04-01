package OzLympicGames.OzlympicGamesMVC.OzlController;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;
import OzLympicGames.OzlympicGamesMVC.OzlModel.GamesAthlete;
import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGame;
import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGamesModel;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameMenu;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by dimi on 11/3/17.
 * General Game Controller Class
 */


public class GameController {
    private final GameView view;
    private OzlGamesModel model1;

    private MenuController currentMenuController;

    public GameController(OzlGamesModel model, GameView view) {
        this.model1 = model;
        this.view = view;
    }

    public void startGames()  {
        currentMenuController = new mainMenuController();
        currentMenuController.takeControl();
    }



    private class mainMenuController extends MenuController {

        private OzlGame currentActiveGame;

        // constructor
        private mainMenuController(){
            super(new HashMap<Integer, MenuController>() {{
                put(1, null);
                put(2, null);
                put(3, null);
                put(4, null);
                put(5, null);
                put(6, new subMenuControllerExit());
            }});
        }

        @Override
        void updateViewMenu() {
            view.setCurrentMenu(
                    new GameMenu("mainMenu")
            );
        }

        @Override
        void takeControl(){
            updateViewMenu();
            updateView();
            setParentForSubmenus();
            int userInput = getUserIntInput();
            currentMenuController = getSubMenuControllers().get(userInput);
            currentMenuController.takeControl();
        }

        @Override
        void updateView() {
            view.updateScreen(
                    getMenuMessage()
            );
        }

        String getMenuMessage() {
            return currentActiveGame == null ? String.format("\n\rGames recorded: %d | Athletes: %d ",
                    model1.getMyOzlGames().size(),
                    model1.getmYgamesAthletes().size())
                    : String.format("\n\r%s $s Total Players: %d", currentActiveGame.getGameId(),
                                GamesHelperFunctions.firsLetterToUpper(currentActiveGame.getGameSportType().name()),
                    Math.toIntExact(Arrays.stream(currentActiveGame.getGameParticipants())
                            .filter(Objects::nonNull)
                            .filter( s -> s instanceof GamesAthlete).count())
                    );
        }


        // set parent for submenus
        void setParentForSubmenus(){
            getSubMenuControllers().values().stream().filter(Objects::nonNull).forEach(menuController -> menuController.setParentMenuController(this));
        }

    }

    private class subMenuControllerExit extends MenuController {

        //using default constructor

        @Override
        void updateViewMenu() {
            view.setCurrentMenu(
                    new GameMenu("exit")
            );
        }

        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            System.exit(0);
        }

        @Override
        void updateView() {
            view.updateScreen();
        }
    }

    private class subMenuControllerGameSelect extends MenuController {

        //using default constructor

        @Override
        void updateViewMenu() {
            view.setCurrentMenu(
                    new GameMenu("exit")
            );
        }

        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            System.exit(0);
        }

        @Override
        void updateView() {
            view.updateScreen();
        }
    }
}



