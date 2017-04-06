package OzLympicGames.OzlympicGamesMVC.OzlController;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
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
    private IOzlConfigRead configReader = OzlConfigRead.getInstance();
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
        private final int BACK_OPTION_COUNT = configReader.getConfigInt("BACK_OPTION_COUNT", "config.properties");
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
                    ? subMenuControllers.get(userInput) // Note to self: It a map!!!! Not List. User Input is key
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
            if (model.getCurrentActiveGame() == null) {
                mainMenuMessage.append(String.format("\n\rGames recorded: %d | Athletes: %d ",
                        model.getMyOzlGames().size(),
                        model.getMyGamesAthletes().size())
                );
            } else {

                mainMenuMessage.append(String.format("\n\r\n\rGames recorded: %d | Athletes: %d ",
                        model.getMyOzlGames().size(),
                        model.getMyGamesAthletes().size())
                );
                int gameAthletesCount = GamesHelperFunctions.athletesCount(model.getCurrentActiveGame());
                if (gameAthletesCount > 0){
                    mainMenuMessage.append(String.format("\n\rCurrent Active Game: %s %s \n\rAssigned athletes: %d" +
                                    "\n\rReferee: %s %s (%s | Age: %d)",
                            model.getCurrentActiveGame().getGameId(),
                            GamesHelperFunctions.firsLetterToUpper(model.getCurrentActiveGame().getGameSportType().name()),
                            gameAthletesCount,
                            model.getCurrentActiveGame().getGameParticipants()[0].getParticipantId(),
                            model.getCurrentActiveGame().getGameParticipants()[0].getParticipantName(),
                            model.getCurrentActiveGame().getGameParticipants()[0].getParticipantState(),
                            model.getCurrentActiveGame().getGameParticipants()[0].getParticipantAge())
                    );
                }  else {
                    mainMenuMessage.append(String.format("\n\rCurrent Active Game: %s %s \n\rAssigned athletes: %d",
                            model.getCurrentActiveGame().getGameId(),
                            GamesHelperFunctions.firsLetterToUpper(model.getCurrentActiveGame().getGameSportType().name()),
                            gameAthletesCount)
                    );
                }

                if (!model.getCurrentActiveGame().getUserPrediction().equals("")) {
                    mainMenuMessage.append("\n\rUser Prediction Athlete ID: ");
                    mainMenuMessage.append(model.getCurrentActiveGame().getUserPrediction());
                }

            }
            return mainMenuMessage.toString();
        }
    }

    // option 1: Select a game to run
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

        //constructor
        private SubMenuControllerNewGame(){
            super(new HashMap<Integer, MenuController>() {{
                      put(1, new SubMenuNewGamePlayersAdd() );
                  }}
            );
        }

        @Override
        void takeControl() {
            if (super.subMenuControllers != null) setParentForSubmenus();
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if (userInput > GameSports.values().length) {
                // go back
                currentMenuController = super.parentMenuController;
            } else {
                // set up a new game with game sport
                model.newGameWithSport( GameSports.values()[userInput - 1]);
                //exit to manual or auto input
                currentMenuController = super.subMenuControllers.get(1);
            }

            currentMenuController.takeControl();
        }

        // set menu to view. Need to override when there is no submenus
        @Override
        void updateViewMenu() {
            int sportsCount = 1;
            StringBuilder mySports = new StringBuilder("Select sport you want to run," +
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

    //option 1_1_1: new game select
    private class SubMenuNewGamePlayersAdd extends MenuController{
        // constructor
        private SubMenuNewGamePlayersAdd() {
            super(new HashMap<Integer, MenuController>() {{
                    put(1, new SubMenuPlayersAddAuto());
                    put(2, new SubMenuPlayersAddManual() );
            }}
            );
            setMenuOption("newGameAthletes");
        }
    }

    // option 1_1_1_1 new game fully populated
    private class SubMenuPlayersAddAuto extends MenuController {
        // constant for going back option menu

        @Override
        void takeControl() {
            model.autoSetupParticipantsNewGame();
            updateViewMenu();
            updateView();
            getUserIntOneInput(); // user input always returns 1
            currentMenuController = super.parentMenuController.parentMenuController.parentMenuController.parentMenuController;
            currentMenuController.takeControl();
        }

        @Override
        void updateViewMenu() {
                StringBuilder myAthletesGames = new StringBuilder("Recorded Athletes," +
                        "===================================,");
                myAthletesGames.append(
                        model.getCurrentActiveGame().getGamePlayersList()
                );

                view.setCurrentMenu(
                        new GameMenu( super.BACK_OPTION_COUNT, myAthletesGames.toString().substring(0, myAthletesGames.length() - 1))
                ); //remove last comma from string builder
        }
    }

    // option 1_1_1_2 new game manually populated
    private class SubMenuPlayersAddManual extends MenuController {
        private final int BACK_OPTION_COUNT = 2; //constant two options
        // get max athletes allowed
        private int currentEnrollment;
        private int maxPlayesAllowed;

        @Override
        void takeControl() {
            // get current athletes count
            currentEnrollment = GamesHelperFunctions.athletesCount(model.getCurrentActiveGame());
            maxPlayesAllowed = model.getCurrentActiveGame().getGameParticipants().length - 1;

            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if ( currentEnrollment != maxPlayesAllowed && userInput == 1) {
                model.autoSetupAthlete();
            }   /*else if ( currentEnrollment <= maxPlayesAllowed && userInput == 2) {
                currentMenuController = super.parentMenuController;
            }*/ else {
                currentMenuController = super.parentMenuController.parentMenuController.parentMenuController.parentMenuController;
            }
            currentMenuController.takeControl();
        }


        @Override
        void updateViewMenu() {

            // see if can add more
            if (currentEnrollment <= maxPlayesAllowed ){
                // get model to generate new athlete

                StringBuilder myAthletes = new StringBuilder("Add A New Athlete," +
                        "===================================,");
                myAthletes.append(
                        currentEnrollment == 0 ? "" : model.getCurrentActiveGame().getGamePlayersList()
                );
                myAthletes.append( currentEnrollment >= maxPlayesAllowed ?  "" : ",1. Add new athlete");

                view.setCurrentMenu(
                        new GameMenu( currentEnrollment >= maxPlayesAllowed ? super.BACK_OPTION_COUNT : BACK_OPTION_COUNT,
                                myAthletes.toString())
                ); //remove last comma from string builder
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

            int maxInputInt;
            if (currentEnrollment == maxPlayesAllowed ){
                maxInputInt = 1;
            }   else {
                maxInputInt = BACK_OPTION_COUNT;
            }

            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }

    }


    // option 1_2: Replay a previous game
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
                model.setCurrentActiveGame(
                        (OzlGame) model.getMyOzlGames().get(userInput - 1)
                );
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

    // option 2: Predict a winner
    private class SubMenuControllerSetPrediction extends MenuController {
        @Override
        void takeControl() {
            updateViewMenu();
            updateView();
            int userInput = getUserIntInput();
            if ( model.getCurrentActiveGame() == null) {
                // go back
                currentMenuController = super.parentMenuController;
            } else {
                if (userInput > GamesHelperFunctions.athletesCount(model.getCurrentActiveGame())) {
                    // go back
                    currentMenuController = super.parentMenuController;
                } else {
                    // set user prediction
                    model.getCurrentActiveGame().setUserPrediction(userInput);
                    // go back
                    currentMenuController = super.parentMenuController;
                }
            }
            currentMenuController.takeControl();
        }

        // set menu to view
        @Override
        void updateViewMenu() {
            if ( model.getCurrentActiveGame() != null) {
                int athletesCount = GamesHelperFunctions.athletesCount(model.getCurrentActiveGame());
                StringBuilder myGames = new StringBuilder("Recorded Athletes," +
                        "===================================,");
                myGames.append(
                        model.getCurrentActiveGame().getGamePlayersList()
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
            if (model.getCurrentActiveGame() != null) {
                maxInputInt += GamesHelperFunctions.athletesCount(model.getCurrentActiveGame());
            }
            if (menuSelection > maxInputInt || menuSelection < 1) {
                System.out.println("\033[31mNo such option."); //red
                return getUserIntInput();
            } else {
                return menuSelection;
            }
        }
    }

    // option 3: Start the game
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
            if (model.getCurrentActiveGame() != null) {

                StringBuilder myGameResults = new StringBuilder("Game Results," +
                        "===================================,");
                myGameResults.append(
                        model.getCurrentActiveGame().gamePlayGetResults()
                );

                // append winner final score with prediction
                myGameResults.append(
                        ((IGamesOfficial)model.getCurrentActiveGame().getGameParticipants()[0]).getGameScore()
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

    // option 4: Display the final results of all games
    private class SubMenuControllerFinalResults extends MenuController {
        // constant for going back option menu


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
                    // if game has athletes
                    if ( GamesHelperFunctions.athletesCount((OzlGame)myGame) > 0) {
                        myGameResults.append(
                                ((IGamesOfficial) ((OzlGame) myGame).getGameParticipants()[0]).getGameScore()
                        );
                    }
                }
                view.setCurrentMenu(
                        new GameMenu(super.BACK_OPTION_COUNT , myGameResults.toString().substring(0, myGameResults.length() - 1))
                ); //remove last comma from string builder
            } else {
                view.setCurrentMenu(
                        new GameMenu("noGames")
                );
            }
        }
    }

    // option 5: Display all points
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
                        .thenComparingInt(GamesAthlete::getTotalPoints).reversed();

                for (GamesAthlete athlete : model.getMyGamesAthletes()
                        .stream().filter(g -> g.getTotalPoints() > 0)
                        .sorted(byTotalPoints)
                        .collect(Collectors.toCollection(ArrayList::new))
                        ) {
                    myGameResults.append(String.format("%s | Total Points: %d\r\n", athlete.getParticipantName(), athlete.getTotalPoints()));
                }
                view.setCurrentMenu(
                        new GameMenu(super.BACK_OPTION_COUNT , myGameResults.toString().substring(0, myGameResults.length() - 1))
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



