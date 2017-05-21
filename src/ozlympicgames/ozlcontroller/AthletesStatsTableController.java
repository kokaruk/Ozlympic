package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Dialogues;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.util.Map;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class AthletesStatsTableController {

    @FXML
    private TableView<Map.Entry<String,GamesAthlete>> athletesTable;
    @FXML
    private TableColumn<Map.Entry<String,GamesAthlete>, String> athlName;
    @FXML
    private TableColumn<Map.Entry<String,GamesAthlete>, String> arthlType;
    @FXML
    private TableColumn<Map.Entry<String,GamesAthlete>, Integer> athlPoints;

    ObservableList<Map.Entry<String, GamesAthlete>> items;

    public void setAthletesTable(GamesAthlete athlete){
        items.add(new java.util.AbstractMap.SimpleEntry<>(athlete.getId(), athlete));
    }

    @FXML
    private AthletesOverviewController athletesOverviewController;


    private static Logger logger = LogManager.getLogger();
    private static IGamesDAL gamesDAL;

    public AthletesStatsTableController() {
        try {
            gamesDAL = GamesDAL.getInstance();
            //gamesTableView = new TableView(FXCollections.observableArrayList(gamesMap.entrySet()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            Dialogues.createExceptionDialog(e);
            System.exit(1);
        }
    }

    public void setGamesOverviewController(AthletesOverviewController athletesOverviewController) {
        this.athletesOverviewController = athletesOverviewController;
    }

    @FXML
    private void initialize() {
        // Initialize the columns.
        athlPoints.setCellValueFactory(cellData -> new SimpleIntegerProperty( cellData.getValue().getValue().getTotalPoints()).asObject() );
        athlName.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getValue().getName()) );

        arthlType.setCellValueFactory(cellData -> new SimpleStringProperty(GamesHelperFunctions.firsLetterToUpper(
                cellData.getValue().getValue().getAthleteType().name()
        ) ) );



        // Add data to the table
        items = FXCollections.observableArrayList(gamesDAL.get_athletes().entrySet());
        athletesTable.setItems(items);

        // Listen for selection changes and show the person details when changed.
        athletesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    logger.trace("Athlete Select " +newValue.getValue().getId());
                    athletesOverviewController.showGameDetails(newValue);
                });

    }
}
