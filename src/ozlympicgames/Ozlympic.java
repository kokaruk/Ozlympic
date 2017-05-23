package ozlympicgames;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlcontroller.AddRefereeController;
import ozlympicgames.ozlcontroller.AthleteAddController;
import ozlympicgames.ozlcontroller.AthletesOverviewController;
import ozlympicgames.ozlcontroller.GamesOverviewController;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.io.IOException;

/**
 * Main class to start the application.
 *
 * @author Marco Jakob
 */
public class Ozlympic extends Application {
    // function class reference
    private static Logger logger = LogManager.getLogger();

    // collection
    private ObservableList<GamesAthlete> _athletes = FXCollections.observableArrayList();
    private ObservableList<OzlGame> _games = FXCollections.observableArrayList();


    // controllers
    private GamesOverviewController gamesOverviewController;
    private AthletesOverviewController athletesOverviewController;
    // stages
    private Stage primaryStage;
    private Parent rootLayout;
    private static IGamesDAL gamesDAL;
    static {
        try {
            gamesDAL = GamesDAL.getInstance();
        } catch (Exception e) {

        }
    }


    public ObservableList<GamesAthlete> get_athletes() {
        return _athletes;
    }

    public ObservableList<OzlGame> get_games() {
        return _games;
    }

    public Ozlympic() {

         if(gamesDAL != null){
             _athletes.addAll(gamesDAL.get_athletes().values());
            _games.addAll(gamesDAL.get_games().values());
         }

    }


    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Ozlympic Games");
        this.primaryStage.setResizable(false);
        try {
            initRootLayout();
            showGamesTable();
            showAthletesTable();

        } catch (Exception e) {
            Dialogues.createExceptionDialog(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/TabbedRoot.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            primaryStage.setScene(new Scene(      new BorderPane(rootLayout), 800, 600));
            primaryStage.show();


    }

    /**
     * Shows overview inside the tab layout.
     */
     void showGamesTable() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/GamesOverview.fxml"));
            AnchorPane anchorPane   = loader.load();
            gamesOverviewController = loader.getController();
            gamesOverviewController.setMainApp(this);
            TabPane tabPane = (TabPane)((AnchorPane) rootLayout).getChildren().get(0);
            tabPane.getTabs().get(0).setContent(anchorPane);
    }

            /**
            * Shows overview inside the tab layout.
            */
    void showAthletesTable() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/AthletesOverview.fxml"));
        SplitPane splitPane  = loader.load();
        athletesOverviewController = loader.getController();
        athletesOverviewController.setMainApp(this);
        // Set athletes overview into the center of root layout.
        TabPane tabPane = (TabPane)((AnchorPane) rootLayout).getChildren().get(0);
        tabPane.getTabs().get(1).setContent(splitPane);
    }


    public boolean showNewAthleteDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/AthleteAdd.fxml"));
            AnchorPane page = loader.load();

            athletesOverviewController.setAthleteAddController(loader.getController());

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Athlete");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            String name = GamesHelperFunctions.getRandomName();
            String state = GamesHelperFunctions.getRandomState();
            int age = GamesHelperFunctions.getRandomAge();

            // Set the person into the controller.
            AthleteAddController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.populsteData(name, state, age );

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showAddRefereeDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/AddReferree.fxml"));
            AnchorPane page = loader.load();

            gamesOverviewController.setAddRefereeController(loader.getController());

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Referee");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            String name = GamesHelperFunctions.getRandomName();
            String state = GamesHelperFunctions.getRandomState();
            int age = GamesHelperFunctions.getRandomAge();

            // Set the person into the controller.
            AddRefereeController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.populsteData(name, state, age );

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



}



