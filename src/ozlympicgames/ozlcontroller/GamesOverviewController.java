package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Dialogues;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;

import java.io.IOException;
import java.sql.SQLException;
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
    private AddRefereeController addRefereeController;

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
    private Label activeGameLabel;
    @FXML
    private Button activateGameButon;
    @FXML
    private Button addRefereeButton;
    @FXML
    private Button addPlayersButton;
    @FXML
    private Button gamePlayButton;

    private OzlGame ozlGame; // temporary game pointer
    private OzlGame activeGame; // game for playing

    public void setAddRefereeController(AddRefereeController addRefereeController) {
        this.addRefereeController = addRefereeController;
    }

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
            // if game never played
            if (entry.isGamePlayed()) {
                activateGameButon.setDisable(true);
            } else  {
                if (ozlGame != activeGame) {
                    activateGameButon.setDisable(false);
                } else {
                    activateGameButon.setDisable(true);
                }
            }
        } else {
            refName.setText(null);
            gameAthletes.setText(null);
            gameWinner.setText(null);
            winnerTime.setText(null);
            activeGameLabel.setText(null);
        //    activeGame = null;
        }
    }

    @FXML
    private void handleActivate(){
        logger.trace("activate click");
        activeGame = ozlGame;
        activeGameLabel.setText(activeGame.getId() + " " + GamesHelperFunctions.firsLetterToUpper(activeGame.getGameSport().name()));
        activateGameButon.setDisable(true);
        if (activeGame.get_referee() == null){
            addRefereeButton.setDisable(false);
            if (!addPlayersButton.isDisabled()) addPlayersButton.setDisable(true);
        } else {
            addPlayersButton.setDisable(false);
            if (!addRefereeButton.isDisabled()) addRefereeButton.setDisable(true);
        }
    }

    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
        // Add data to the table
        gamesTable.setItems(mainApp.get_games());

    }

    @FXML
    void handleAddReferee(){
        logger.trace("add referee");
        boolean okClicked = mainApp.showAddRefereeDialog();
        if (okClicked) {
            try {
                mainApp.addRefereeToGame(activeGame, addRefereeController.getGamesReferee());
                activeGame.addParticipant(addRefereeController.getGamesReferee());
                addRefereeButton.setDisable(true);
            } catch (IOException | SQLException | ClassNotFoundException e) {
                logger.fatal(e.getMessage());
                Dialogues.createExceptionDialog(e);
            }
        }
        if (ozlGame==activeGame) showGameDetails(activeGame);
    }

    @FXML
    void handleAddplayers(){
        logger.trace("add athletes");
    }

    @FXML
    void handlePlayGame(){
        logger.trace("play");

    }

}
