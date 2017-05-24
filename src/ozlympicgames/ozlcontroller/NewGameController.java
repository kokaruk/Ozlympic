package ozlympicgames.ozlcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.GameSports;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 24/5/17.
 */
public class NewGameController {
    private static Logger logger = LogManager.getLogger();
    private static IGamesDAL gamesDAL;

    static {
        try {
            gamesDAL = GamesDAL.getInstance();
        } catch (Exception e) {
            logger.trace(e.getMessage());
            // do nothing
        }

    }

    @FXML
    private ComboBox<GameSports> gameSportType;  //conbobox
    private ObservableList<GameSports> gameSportTypeData = FXCollections.observableArrayList(GameSports.values()); //comboboxvalues
    private Stage dialogStage;
    private OzlGame game;
    private boolean okClicked = false;

    public OzlGame getGame() {
        return game;
    }

    @FXML
    private void initialize() {
        // Init ComboBox items.
        gameSportType.setItems(gameSportTypeData);

        // Define rendering of the list of values in ComboBox drop down.
        gameSportType.setCellFactory(comboBox -> {
            return new ListCell<GameSports>() {
                @Override
                protected void updateItem(GameSports item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.name());
                    }
                }
            };
        });

        // Define rendering of selected value shown in ComboBox.
        gameSportType.setConverter(new StringConverter<GameSports>() {
            @Override
            public String toString(GameSports gameSports) {
                if (gameSports == null) {
                    return null;
                } else {
                    return gameSports.name();
                }
            }

            @Override
            public GameSports fromString(String sportTypeString) {
                return null; // No conversion fromString needed.
            }
        });

        // Handle ComboBox event.
        gameSportType.setOnAction((event) -> {
            GameSports selectedPerson = gameSportType.getSelectionModel().getSelectedItem();
            logger.trace(selectedPerson.name());
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws SQLException, ClassNotFoundException, IOException {
        if (isInputValid()) {
            game = gamesDAL.getGameDAO().getNewGame(
                    gameSportType.getSelectionModel().getSelectedItem().name()
            );

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (gameSportType.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Please Select Sport";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Game Sport");
            alert.setHeaderText("Please correct Game Sport");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}

