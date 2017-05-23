package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class GamesOverviewController {
    private TableRootController tableRootController;
    private static Logger logger = LogManager.getLogger();
    private Ozlympic mainApp;

    @FXML
    private TableView<OzlGame> gamesTable;
    @FXML
    private TableColumn<OzlGame, String> gameIdColumn;
    @FXML
    private TableColumn<OzlGame, String> gameSportColumn;
    @FXML
    private TableColumn<OzlGame, LocalDateTime> timeRunColumn;

    @FXML
    private Label refName;
    @FXML
    private Label gameAthletes;
    @FXML
    private Label gameWinner;
    @FXML
    private Label winnerTime;
    @FXML
    private Button activateGame;

    @FXML
    private Label activeGame;
    @FXML
    private Button addReferee;
    @FXML
    private Button addPlayers;
    @FXML
    private Button gamePlay;


    OzlGame ozlGame;

    @FXML
    private void initialize() {
        showGameDetails(null);
        // Initialize the columns.
        gameIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getId()) );
        gameSportColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(GamesHelperFunctions.firsLetterToUpper(
                        cellData.getValue().getGameSport().name()
                ) ) );
        timeRunColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>( cellData.getValue().getTimeRun()) );

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Custom rendering of the table cell.
        timeRunColumn.setCellFactory(column -> {
            return new TableCell<OzlGame, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText("Game Never Run");
                        setStyle("-fx-background-color: lightgrey ");
                    } else {
                        // Format date.
                        setText(myDateFormatter.format(item));
                        setStyle("");
                    }
                }
            };
        });

// Listen for selection changes and show the game details when changed.
        gamesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    logger.trace("Game Select " +newValue.getId());
                    showGameDetails(newValue);
                });

    }

    public void setTableRootController(TableRootController tableRootController) {
        this.tableRootController = tableRootController;
    }

    void showGameDetails(OzlGame entry){
        if(entry != null){
            this.ozlGame = entry;
            logger.trace("Game Accept: " + entry.getId());
            refName.setText ( entry.get_referee() != null ? entry.get_referee().getName() : null);
            gameAthletes.setText( entry.getParticipation().size() > 0 ? "" + entry.getParticipation().size() : null);
            gameWinner.setText( entry.isGamePlayed() ? entry.getGameAthletesWinnersSortedByTime().get(0).getName() : null   );
            activateGame.setDisable(false);
            activeGame.setText(null);
        } else {
            refName.setText(null);
            gameAthletes.setText(null);
            gameWinner.setText(null);
            winnerTime.setText(null);
        }
    }

    @FXML
    private void handleActivate(){
        logger.trace("activate click");
        tableRootController.showGameDetails(ozlGame);
    }

    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
        // Add data to the table
        gamesTable.setItems(mainApp.get_games());

    }
}
