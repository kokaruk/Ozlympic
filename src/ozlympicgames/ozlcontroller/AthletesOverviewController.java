package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesHelperFunctions;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class AthletesOverviewController {

    private static Logger logger = LogManager.getLogger();
    private Ozlympic mainApp;
    AthleteEditController athleteEditController;

    @FXML
    private TableView<GamesAthlete> athletesTable;
    @FXML
    private TableColumn<GamesAthlete, String> athlName2;
    @FXML
    private TableColumn<GamesAthlete, String> arthlType;
    @FXML
    private TableColumn<GamesAthlete, Integer> athlPoints;

    @FXML
    private Label athlNameLabel;
    @FXML
    private Label typeAthl;
    @FXML
    private Label athlState;
    @FXML
    private Label totalAge;


    @FXML
    private void handleNewAthlete() {
        boolean okClicked = mainApp.showPersonEditDialog();
        if (okClicked) {
            // not working
            mainApp.get_athletes().add(
                    athleteEditController.getGamesAthlete()
            );
        }
    }

    @FXML
    private void initialize() {
        showGameDetails(null);
       // Initialize the columns.
        athlPoints.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getTotalPoints()).asObject() );
        athlName2.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getName()) );

        arthlType.setCellValueFactory(cellData -> new SimpleStringProperty(GamesHelperFunctions.firsLetterToUpper(
                cellData.getValue().getAthleteType().name()
        ) ) );

        // Listen for selection changes and show the person details when changed.
        athletesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    logger.trace("Athlete Select " +newValue.getId());
                    showGameDetails(newValue);
                });


    }

    void showGameDetails(GamesAthlete entry) {
        if (entry != null) {
            logger.trace("Athlete Accept: " + entry.getId());
            athlNameLabel.setText(entry.getName());
            typeAthl.setText(GamesHelperFunctions.firsLetterToUpper(entry.getAthleteType().name()));
            athlState.setText(entry.getState());
            totalAge.setText(entry.getAge().toString());
        } else {
            athlNameLabel.setText(null);
            typeAthl.setText(null);
            athlState.setText(null);
            totalAge.setText(null);
        }
    }
    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
        // Add data to the table
        //items = FXCollections.observableArrayList(gamesDAL.get_athletes().entrySet());
        athletesTable.setItems( mainApp.get_athletes());
    }

    public void setAthleteEditController (AthleteEditController athleteEditController){
        this.athleteEditController = athleteEditController;
    }
}