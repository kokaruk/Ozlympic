package ozlympicgames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlcontroller.*;
import ozlympicgames.ozlmodel.GamesHelperFunctions;

import java.io.IOException;

/**
 * Main class to start the application.
 *
 * @author Marco Jakob
 */
public class Ozlympic extends Application {
    private static Logger logger = LogManager.getLogger();


    // controllers
    private GamesOverviewController gamesOverviewController;
    private GameStatsTableController gameStatsTableController;
    private AthletesOverviewController athletesOverviewController;
    private AthletesStatsTableController athletesStatsTableController;
    private TableRootController tableRootController;
    // stages
    private Stage primaryStage;
    private Parent rootLayout;

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Ozlympic Games");
        this.primaryStage.setResizable(false);
        try {
            initRootLayout();
            showGamesTable();
            showAthletesTable();
            athletesOverviewController.setMainApp(this);
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
            tableRootController = loader.getController();
            // Show the scene containing the root layout.
            primaryStage.setScene(new Scene(      new BorderPane(rootLayout), 800, 600));
            primaryStage.show();


    }

    /**
     * init Games Overview Layout
     */
    public void initGamesOverview(){

    }

    /**
     * Shows overview inside the tab layout.
     */
     void showGamesTable() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/GamesOverview.fxml"));
            SplitPane splitPane  = loader.load();
            gamesOverviewController = loader.getController();
            splitPane.setDividerPositions(0.4);
            // Set person overview into the center of root layout.
            splitPane.lookupAll(".split-pane-divider")
                    .forEach(div ->  div.setMouseTransparent(true) );

            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("ozlview/GameStatsTable.fxml"));
            //  StackPane leftPane = new StackPane(new Label("Left"));
            AnchorPane leftPane = loader2.load();
            //Constrain max size of left component:
            leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.4));
            gameStatsTableController = loader2.getController();
            gameStatsTableController.setGamesOverviewController(gamesOverviewController);
            gamesOverviewController.setTableRootController(tableRootController);
            splitPane.getItems().set(0,leftPane);

            TabPane tabPane = (TabPane)((AnchorPane) rootLayout).getChildren().get(0);
            VBox vbox = (VBox) tabPane.getTabs().get(0).getContent();
            AnchorPane pane = (AnchorPane)vbox.getChildren().get(1);
            pane.getChildren().add(splitPane);


    }

            /**
            * Shows overview inside the tab layout.
            */
    void showAthletesTable() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/AthletesOverview.fxml"));
        SplitPane splitPane  = loader.load();
        athletesOverviewController = loader.getController();
        splitPane.setDividerPositions(0.4);
        // Set person overview into the center of root layout.


        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("ozlview/AthletesStatsTable.fxml"));
        //  StackPane leftPane = new StackPane(new Label("Left"));
        AnchorPane leftPane = loader2.load();
        //Constrain max size of left component:
        leftPane.maxWidthProperty().bind(splitPane.widthProperty().multiply(0.4));
        athletesStatsTableController = loader2.getController();
        athletesStatsTableController.setGamesOverviewController(athletesOverviewController);
        athletesOverviewController.setAthletesStatsTableController(athletesStatsTableController);
        splitPane.getItems().set(0,leftPane);

        TabPane tabPane = (TabPane)((AnchorPane) rootLayout).getChildren().get(0);
        tabPane.getTabs().get(1).setContent(splitPane);
    }


    public boolean showPersonEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ozlview/AthleteEdit.fxml"));
            AnchorPane page = loader.load();

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
            AthleteEditController controller = loader.getController();
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



