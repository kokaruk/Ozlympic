package OzLympicGames.OzlympicGamesMVC.OzlController;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;
import OzLympicGames.OzlympicGamesMVC.OzlModel.*;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameMenu;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dimi on 11/3/17.
 * General Game Controller Class
 */


public class GameController {
    private final GameView view;
    private final OzlGamesModel model;

    private MenuController currentMenuController;

    public GameController(OzlGamesModel model, GameView view) {
        this.model = model;
        this.view = view;
    }

    public void startGames() {
        currentMenuController = new MainMenuController();
        currentMenuController.takeControl();
    }

    // abstract for all menus controller
    private abstract class MenuController {
        // Menu Option Property to get from data file
        private String menuOption = "";

        public void setMenuOption(String menuOption) {
            this.menuOption = menuOption;
        }

        // Sub Menus Map
        private Map<Integer, MenuController> subMenuControllers;


        // parent sub menu
        private MenuController parentMenuController;

        private void setParentMenuController(MenuController parentMenuController) {
            this.parentMenuController = parentMenuController;
        }

        // no arguments constructor
        MenuController() {
        }

        // constructor
        MenuController(HashMap<Integer, MenuController> subMenuControllers) {
            this.subMenuControllers = subMenuControllers;
        }

        // take control method to be called by instantiation or sub menu on return even
        void takeControl() {
            if (subMenuControllers != null) setParentForSubmenus();
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            currentMenuController = userInput <= subMenuControllers.size()
                    ? subMenuControllers.get(userInput)
                    : parentMenuController;
            currentMenuController.takeControl();
        }

        // set parent menu for submenus
        void setParentForSubmenus() {
            subMenuControllers.values().stream().filter(Objects::nonNull)
                    .forEach(menuController -> menuController.setParentMenuController(this));
        }

        // set menu to view
        void updateViewMenu() {
            view.setCurrentMenu(
                    parentMenuController == null ? new GameMenu(menuOption) : new GameMenu(menuOption, subMenuControllers.size() + 1)
            );
        }

        // update current screen
        void updateView() {
            // if parent not null,
            view.updateScreen();
        }

        // scan for user input, call recursively, unit received an integer.
        int getUserIntInput() {
            System.out.println("");
            System.out.println("");
            System.out.print("\033[32mMake a choice: "); //green
            Scanner scanner = new Scanner(System.in);
            String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

            int menuSelection;

            try {
                menuSelection = Integer.parseInt(menuSelectionString);
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
                return getUserIntInput();
            }
            int maxInputInt = parentMenuController == null ? subMenuControllers.size() : subMenuControllers.size() + 1;
            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }

        // scan for user input, allowing only for number one, needed for menus with return only
        @SuppressWarnings("UnusedReturnValue") // unused value but need user interaction
        int getUserIntOneInput() {
            System.out.println("");
            System.out.println("");
            System.out.print("\033[32mMake a choice: "); //green
            Scanner scanner = new Scanner(System.in);
            String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

            int menuSelection;

            try {
                menuSelection = Integer.parseInt(menuSelectionString);
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
                return getUserIntOneInput();
            }
            int maxInputInt = 1;

            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntOneInput();
            } else {
                return menuSelection;
            }
        }
    }

    // Main Menu
    private class MainMenuController extends MenuController {

        private OzlGame currentActiveGame;

        // constructor
        private MainMenuController() {
            super(new HashMap<Integer, MenuController>() {{
                      put(1, new SubMenuControllerGameSelect());
                      put(2, new SubMenuControllerSetPrediction());
                      put(3, new SubMenuControllerStartGame());
                      put(4, new SubMenuControllerFinalResults());
                      put(5, new SubMenuControllerAllPoints());
                      put(6, new SubMenuControllerExit());
                  }}
            );
            setMenuOption("mainMenu");
        }

        @Override
        void updateView() {
            view.updateScreen(
                    getMenuMessage()
            );
        }

        String getMenuMessage() {
            StringBuilder mainMenuMessage = new StringBuilder();
            if (currentActiveGame == null) {
                mainMenuMessage.append(String.format("\n\rGames recorded: %d | Athletes: %d ",
                        model.getMyOzlGames().size(),
                        model.getMyGamesAthletes().size())
                );
            } else {

                mainMenuMessage.append(String.format("\n\r\n\rGames recorded: %d | Athletes: %d ",
                        model.getMyOzlGames().size(),
                        model.getMyGamesAthletes().size())
                );

                int gamePlayers = Math.toIntExact(Arrays.stream(currentActiveGame.getGameParticipants())
                        .filter(Objects::nonNull)
                        .filter(s -> s instanceof GamesAthlete).count());
                mainMenuMessage.append(String.format("\n\rCurrent Active Game: %s %s \n\rAssigned athletes: %d" +
                                "\n\rReferee: %s %s (%s | Age: %d)",
                        currentActiveGame.getGameId(),
                        GamesHelperFunctions.firsLetterToUpper(currentActiveGame.getGameSportType().name()),
                        gamePlayers,
                        currentActiveGame.getGameParticipants()[0].getParticipantId(),
                        currentActiveGame.getGameParticipants()[0].getParticipantName(),
                        currentActiveGame.getGameParticipants()[0].getParticipantState(),
                        currentActiveGame.getGameParticipants()[0].getParticipantAge())
                );
                if (!currentActiveGame.getUserPrediction().equals("")) {
                    mainMenuMessage.append("\n\rUser Prediction Athlete ID: ");
                    mainMenuMessage.append(currentActiveGame.getUserPrediction());
                }

            }
            return mainMenuMessage.toString();
        }
    }

    // option 1
    private class SubMenuControllerGameSelect extends MenuController {

        private SubMenuControllerGameSelect() {
            super(new HashMap<Integer, MenuController>() {{
                      put(1, new SubMenuControllerNewGame());
                      put(2, new SubMenuControllerPreviousGame());
                  }}
            );
            setMenuOption("selectGame");
        }
    }

    // option 1_1
    private class SubMenuControllerNewGame extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if (userInput > GameSports.values().length) {
                // go back
                currentMenuController = super.parentMenuController;
            } else {
                // set up a new game with game sport
                ((MainMenuController) super.parentMenuController.parentMenuController).currentActiveGame
                        = model.newGameWithSport(
                        GameSports.values()[userInput - 1]
                );
                //exit to main menu
                currentMenuController = super.parentMenuController.parentMenuController;
            }

            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            int sportsCount = 1;
            StringBuilder mySports = new StringBuilder("System will summon new participants for you," +
                    "===================================,");
            for (GameSports sport : GameSports.values()) {
                mySports.append(
                        String.format("%d. %s,", sportsCount,
                                GamesHelperFunctions.firsLetterToUpper(sport.toString()))
                );
                sportsCount++;
            }

            view.setCurrentMenu(
                    new GameMenu(sportsCount, mySports.toString().substring(0, mySports.length() - 1))
            );
        }

        @Override
        int getUserIntInput() {
            System.out.println("");
            System.out.println("");
            System.out.print("\033[32mMake a choice: "); //green
            Scanner scanner = new Scanner(System.in);
            String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

            int menuSelection;

            try {
                menuSelection = Integer.parseInt(menuSelectionString);
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
                return getUserIntInput();
            }
            int maxInputInt = GameSports.values().length + 1;
            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }
    }

    // option 1_2
    private class SubMenuControllerPreviousGame extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if (userInput > model.getMyOzlGames().size()) {
                // go back
                currentMenuController = super.parentMenuController;
            } else {
                // set up a new game with game sport
                ((MainMenuController) super.parentMenuController.parentMenuController).currentActiveGame
                        = (OzlGame) model.getMyOzlGames().get(userInput - 1);
                //exit to main menu
                currentMenuController = super.parentMenuController.parentMenuController;
            }

            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            int gamesCount = 1;
            StringBuilder myGames = new StringBuilder("Recorded Games List," +
                    "===================================,");
            for (IOzlGame myGame : model.getMyOzlGames()) {
                myGames.append(
                        String.format("%d: %s %s,", gamesCount, myGame.getGameId(),
                                GamesHelperFunctions.firsLetterToUpper(((OzlGame) myGame).getGameSportType().name()))
                );
                gamesCount++;
            }

            view.setCurrentMenu(
                    new GameMenu(gamesCount, myGames.toString().substring(0, myGames.length() - 1))
            ); //remove last comma from string builder
        }

        @Override
        int getUserIntInput() {
            System.out.println("");
            System.out.println("");
            System.out.print("\033[32mMake a choice: "); //green
            Scanner scanner = new Scanner(System.in);
            String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

            int menuSelection;

            try {
                menuSelection = Integer.parseInt(menuSelectionString);
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
                return getUserIntInput();
            }
            int maxInputInt = model.getMyOzlGames().size() + 1;
            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }
    }

    // option 2
    private class SubMenuControllerSetPrediction extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if (((MainMenuController) super.parentMenuController).currentActiveGame == null) {
                // go back
                currentMenuController = super.parentMenuController;
            } else {
                if (userInput > Math.toIntExact(Arrays.stream(((MainMenuController) super.parentMenuController).currentActiveGame.getGameParticipants())
                        .filter(Objects::nonNull)
                        .filter(s -> s instanceof GamesAthlete).count())) {
                    // go back
                    currentMenuController = super.parentMenuController;
                } else {
                    // set user prediction
                    ((MainMenuController) super.parentMenuController).currentActiveGame.setUserPrediction(userInput);
                    // go back
                    currentMenuController = super.parentMenuController;
                }
            }
            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            if (((MainMenuController) super.parentMenuController).currentActiveGame != null) {
                int athletesCount = Math.toIntExact(Arrays.stream(((MainMenuController) super.parentMenuController).currentActiveGame.getGameParticipants())
                        .filter(Objects::nonNull)
                        .filter(s -> s instanceof GamesAthlete).count());
                StringBuilder myGames = new StringBuilder("Recorded Athletes," +
                        "===================================,");
                myGames.append(
                        ((MainMenuController) super.parentMenuController).currentActiveGame.getGamePlayersList()
                );

                view.setCurrentMenu(
                        new GameMenu(++athletesCount, myGames.toString().substring(0, myGames.length() - 1))
                ); //remove last comma from string builder
            } else {
                view.setCurrentMenu(
                        new GameMenu("noGames")
                );
            }
        }

        @Override
        int getUserIntInput() {
            System.out.println("");
            System.out.println("");
            System.out.print("\033[32mMake a choice: "); //green
            Scanner scanner = new Scanner(System.in);
            String menuSelectionString = scanner.nextLine().replaceAll(" +", "");

            int menuSelection;

            try {
                menuSelection = Integer.parseInt(menuSelectionString);
            } catch (NumberFormatException e) {
                System.out.println("\033[31mInvalid input format.\r\nNumbers Only."); //red
                return getUserIntInput();
            }
            int maxInputInt = 1;
            if (((MainMenuController) super.parentMenuController).currentActiveGame != null) {
                maxInputInt += Math.toIntExact(Arrays.stream(((MainMenuController) super.parentMenuController).currentActiveGame.getGameParticipants())
                        .filter(Objects::nonNull)
                        .filter(s -> s instanceof GamesAthlete).count());
            }
            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }
    }

    // option 3
    private class SubMenuControllerStartGame extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            getUserIntOneInput(); // always returns 1, still need user action
            currentMenuController = super.parentMenuController;
            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            if (((MainMenuController) super.parentMenuController).currentActiveGame != null) {

                StringBuilder myGameResults = new StringBuilder("Game Results," +
                        "===================================,");
                myGameResults.append(
                        ((MainMenuController) super.parentMenuController).currentActiveGame.gamePlayGetResults()
                );

                // append winner final score with prediction
                myGameResults.append(
                        ((IGamesOfficial) ((MainMenuController) super.parentMenuController).currentActiveGame.getGameParticipants()[0]).getGameScore()
                );

                view.setCurrentMenu(
                        new GameMenu(1, myGameResults.toString().substring(0, myGameResults.length() - 1))
                ); //remove last comma from string builder
            } else {
                view.setCurrentMenu(
                        new GameMenu("noGames")
                );
            }
        }
    }

    // option 4
    private class SubMenuControllerFinalResults extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            getUserIntOneInput();  // user input returns 1 only
            currentMenuController = super.parentMenuController;
            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            if (model.getMyOzlGames().size() > 0) {

                StringBuilder myGameResults = new StringBuilder("All Game Winners," +
                        "===================================,");

                for (IOzlGame myGame : model.getMyOzlGames()) {
                    myGameResults.append(
                            ((IGamesOfficial) ((OzlGame) myGame).getGameParticipants()[0]).getGameScore()
                    );
                }
                view.setCurrentMenu(
                        new GameMenu(1, myGameResults.toString().substring(0, myGameResults.length() - 1))
                ); //remove last comma from string builder
            } else {
                view.setCurrentMenu(
                        new GameMenu("noGames")
                );
            }
        }
    }

    // option 5
    private class SubMenuControllerAllPoints extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            getUserIntOneInput(); // user input always returns 1
            currentMenuController = super.parentMenuController;
            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            if (model.getMyOzlGames().size() > 0) {

                StringBuilder myGameResults = new StringBuilder("Game Results for all athletes with points," +
                        "===================================,");
                // arrange by finish time
                Comparator<GamesAthlete> byTotalPoints = Comparator
                        .comparingInt(GamesAthlete::getTotalPoints)
                        .thenComparingDouble(GamesAthlete::getTotalPoints).reversed();

                for (GamesAthlete athlete : model.getMyGamesAthletes()
                        .stream().filter(g -> g.getTotalPoints() > 0)
                        .sorted(byTotalPoints)
                        .collect(Collectors.toCollection(ArrayList::new))
                        ) {
                    myGameResults.append(String.format("%s | Total Points: %d\r\n", athlete.getParticipantName(), athlete.getTotalPoints()));
                }
                view.setCurrentMenu(
                        new GameMenu(1, myGameResults.toString().substring(0, myGameResults.length() - 1))
                ); //remove last comma from string builder
            } else {
                view.setCurrentMenu(
                        new GameMenu("noGames")
                );
            }
        }
    }

    // option 6
    private class SubMenuControllerExit extends MenuController {
        //using default constructor

        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            System.exit(0);
        }

        // set menu to view
        void updateViewMenu() {
            view.setCurrentMenu(new GameMenu("exit")
            );
        }

    }

}



