package ozlympicgames.ozlcontroller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.ListSelectionView;
import ozlympicgames.Dialogues;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.CSVUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class GamesOverviewController {
    private static Logger logger = LogManager.getLogger();
    private Ozlympic mainApp;
    // dialogues controllers
    private AddRefereeController addRefereeController;
    private NewGameController newGameController;
    private AddPlayersController addPlayersController;
    private GamePlayController gamePlayController;
    private ListSelectionView<GamesAthlete> view;


    @FXML
    private TableView<OzlGame> gamesTable;
    @FXML
    private TableColumn<OzlGame, String> gameIdColumn;
    @FXML
    private TableColumn<OzlGame, String> gameSportColumn;
    @FXML
    private TableColumn<OzlGame, LocalDateTime> timeRunColumn;

    @FXML
    private Label gameDetails;
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
    private Button activateGameButton;

    @FXML
    private Button addRefereeButton;
    @FXML
    private Button addPlayersButton;
    @FXML
    private Button gamePlayButton;

    private OzlGame ozlGame; // temporary game pointer
    private OzlGame activeGame; // game for playing

    public OzlGame getActiveGame() {
        return activeGame;
    }

    public void setAddRefereeController(AddRefereeController addRefereeController) {
        this.addRefereeController = addRefereeController;
    }

    public void setNewGameController(NewGameController newGameController) {
        this.newGameController = newGameController;
    }

    public void setAddPlayersController(AddPlayersController addPlayersController) {
        this.addPlayersController = addPlayersController;
    }

    public void setGamePlayController(GamePlayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
        // Add data to the table
        gamesTable.setItems(mainApp.get_games());

    }

    public void setView(ListSelectionView<GamesAthlete> view) {
        this.view = view;
    }

    @FXML
    private void initialize() {
        showGameDetails(null);
        // Initialize the columns.
        gameIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        gameSportColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(GamesHelperFunctions.firsLetterToUpper(
                        cellData.getValue().getGameSport().name()
                )));
        timeRunColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTimeRun()));

        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        // Custom rendering of the table cell.
        timeRunColumn.setCellFactory(column -> {
            return new TableCell<OzlGame, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText("  ");
                    } else {
                        // Format date.
                        setStyle("-fx-background-color: lightgrey ");
                        setText(myDateFormatter.format(item));
                        setStyle("");
                    }
                }
            };
        });

// Listen for selection changes and show the game details when changed.
        gamesTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    logger.trace("Game Select " + newValue.getId());
                    showGameDetails(newValue);
                });

    }

    void showGameDetails(OzlGame entry) {
        if (entry != null) {
            this.ozlGame = entry;
            logger.trace("Game Accept: " + entry.getId());
            gameDetails.setText(entry.getId() + " " + GamesHelperFunctions.firsLetterToUpper(entry.getGameSport().name()));
            refName.setText(entry.get_referee() != null ? entry.get_referee().getName() : null);
            gameAthletes.setText(entry.getParticipation().size() > 0 ? "" + entry.getParticipation().size() : null);
            // if game never played
            if (entry.isGamePlayed()) {
                gameWinner.setText(entry.getGameAthletesWinnersSortedByTime().get(0).getName());
                winnerTime.setText(CSVUtils.parseLine(entry.get_referee().getGameScore(entry).get(0)).get(3));
                activateGameButton.setDisable(true);
            } else {
                gameWinner.setText(null);
                winnerTime.setText(null);

                if (ozlGame != activeGame) {
                    activateGameButton.setDisable(false);
                } else {
                    activateGameButton.setDisable(true);
                }
                gamesTable.refresh();
            }
        } else {
            gameDetails.setText(null);
            refName.setText(null);
            gameAthletes.setText(null);
            gameWinner.setText(null);
            winnerTime.setText(null);
            activeGameLabel.setText(null);
            //    activeGame = null;
        }
    }

    @FXML
    private void handleActivate() {
        logger.trace("activate click");
        activeGame = ozlGame;
        activeGameLabel.setText(activeGame.getId() + " " + GamesHelperFunctions.firsLetterToUpper(activeGame.getGameSport().name()));
        activateGameButton.setDisable(true);
        if (activeGame.get_referee() == null) {
            addRefereeButton.setDisable(false);
            if (!addPlayersButton.isDisabled()) addPlayersButton.setDisable(true);
        } else {
            addPlayersButton.setDisable(false);
            if (!addRefereeButton.isDisabled()) addRefereeButton.setDisable(true);
        }
        if (activeGame.getMIN_PARTICIPANTS() <= activeGame.getGameAthletes().size() && activeGame.get_referee() != null) {
            gamePlayButton.setDisable(false);
        } else {
            gamePlayButton.setDisable(true);
        }
        if (activeGame.getMAX_PARTICIPANTS() == activeGame.getGameAthletes().size() && !addPlayersButton.isDisabled())
            addPlayersButton.setDisable(true);
        if (activeGame.isGamePlayed()) gamePlayButton.setDisable(true);

    }

    @FXML
    private void handleNewGame() {
        logger.trace("New Game Click");
        boolean okClicked = mainApp.showNewGameDialog();
        if (okClicked) {
            ozlGame = newGameController.getGame();
            showGameDetails(ozlGame);
            handleActivate();
            mainApp.get_games().add(activeGame);
            gamesTable.scrollTo(activeGame);
            gamesTable.getSelectionModel().selectLast();
        }
    }

    @FXML
    void handleAddReferee() {
        logger.trace("add referee");
        boolean okClicked = mainApp.showAddRefereeDialog();
        if (okClicked) {
            try {
                mainApp.addRefereeToGameDBRecord(activeGame, addRefereeController.getGamesReferee());
                activeGame.addParticipant(addRefereeController.getGamesReferee());
                addRefereeButton.setDisable(true);
                if (addPlayersButton.isDisabled()) addPlayersButton.setDisable(false);
            } catch (IOException | SQLException | ClassNotFoundException e) {
                logger.fatal(e.getMessage());
                Dialogues.createExceptionDialog(e);
            }
        }
        if (ozlGame == activeGame) showGameDetails(activeGame);
    }

    @FXML
    void handleAddplayers() {
        logger.trace("add athletes");
        boolean okClicked = mainApp.showAddPlayersDialog(activeGame);
        if (okClicked) {
            logger.trace("ok clicked");
            if (view.getTargetItems().size() > 0) {
                view.getTargetItems().stream()
                        .filter(gamesAthlete -> !activeGame.getGameAthletes().contains(gamesAthlete))
                        .limit(ozlGame.getMAX_PARTICIPANTS() - activeGame.getGameAthletes().size())
                        .forEach(athlete -> {
                            try {
                                activeGame.addParticipant(athlete);
                                mainApp.addAthletesToGameDBRecord(activeGame, athlete);
                            } catch (Exception e) {
                                // do nothig, should never trigger
                            }
                        });
                if (ozlGame == activeGame) showGameDetails(activeGame);
                handleActivate();
            }

        }
    }

    @FXML
    void handlePlayGame() {
        logger.trace("play");
        boolean okClicked = mainApp.showGamePlayDialog();
        if (okClicked) {
            showGameDetails(activeGame);
            handleActivate();
            gamesTable.scrollTo(activeGame);
            mainApp.getAthletesOverviewController().refreshTable();
            gamesTable.refresh();
        }

    }


}
