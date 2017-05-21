package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Dialogues;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class GameStatsTableController {

    @FXML
    private TableView<Map.Entry<String,OzlGame>> gamesTable;
    @FXML
    private TableColumn<Map.Entry<String,OzlGame>, String> gameIdColumn;
    @FXML
    private TableColumn<Map.Entry<String,OzlGame>, String> gameSportColumn;
    @FXML
    private TableColumn<Map.Entry<String,OzlGame>, LocalDateTime> timeRunColumn;


    private GamesOverviewController gamesOverviewController;


    private static Logger logger = LogManager.getLogger();
    private static IGamesDAL gamesDAL;

    public GameStatsTableController() {
        try {
            gamesDAL = GamesDAL.getInstance();
            //gamesTableView = new TableView(FXCollections.observableArrayList(gamesMap.entrySet()));
        } catch (Exception e) {
            logger.error(e.getMessage());
            Dialogues.createExceptionDialog(e);
            System.exit(1);
        }
    }

    public void setGamesOverviewController(GamesOverviewController gamesOverviewController) {
        this.gamesOverviewController = gamesOverviewController ;
    }



    @FXML
    private void initialize() {
        // Initialize the columns.
        gameIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getValue().getId()) );
        gameSportColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(GamesHelperFunctions.firsLetterToUpper(
                        cellData.getValue().getValue().getGameSport().name()
                ) ) );
        timeRunColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>( cellData.getValue().getValue().getTimeRun()) );

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Custom rendering of the table cell.
        timeRunColumn.setCellFactory(column -> {
            return new TableCell<Map.Entry<String,OzlGame>, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText("");
                        setStyle("-fx-background-color: lightgrey ");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                        setStyle("");
                    }
                }
            };
        });

        // Add data to the table

        ObservableList<Map.Entry<String, OzlGame>> items = FXCollections.observableArrayList(gamesDAL.get_games().entrySet());
        gamesTable.setItems(items);

        // Listen for selection changes and show the game details when changed.
        gamesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    logger.trace("Game Select " +newValue.getValue().getId());
                    gamesOverviewController.showGameDetails(newValue);
                });

    }
}
