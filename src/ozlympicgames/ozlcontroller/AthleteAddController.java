package ozlympicgames.ozlcontroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.AthleteType;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 22/5/17.
 */
public class AthleteAddController {

    @FXML
    private Label athlName;
    @FXML
    private Label athlState;
    @FXML
    private Label athlAge;
    @FXML
    private ComboBox<AthleteType> athlType;  //conbobox
    private ObservableList<AthleteType> athlTypeData = FXCollections.observableArrayList(AthleteType.values()); //comboboxvalues

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


    private Stage dialogStage;
    private GamesAthlete gamesAthlete;
    private boolean okClicked = false;



    public void populsteData(String name, String state, int age) {
        athlName.setText(name);
        athlState.setText(state);
        athlAge.setText( Integer.toString(age));
    }

    public GamesAthlete getGamesAthlete() {
        return gamesAthlete;
    }

    @FXML
    private void initialize(){
        // Init ComboBox items.
        athlType.setItems(athlTypeData);

        // Define rendering of the list of values in ComboBox drop down.
        athlType.setCellFactory((comboBox) -> {
            return new ListCell<AthleteType>() {
                @Override
                protected void updateItem(AthleteType item, boolean empty) {
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
        athlType.setConverter(new StringConverter<AthleteType>() {
            @Override
            public String toString(AthleteType athleteType) {
                if (athleteType == null) {
                    return null;
                } else {
                    return athleteType.name();
                }
            }

            @Override
            public AthleteType fromString(String personString) {
                return null; // No conversion fromString needed.
            }
        });

        // Handle ComboBox event.
        athlType.setOnAction((event) -> {
            AthleteType selectedPerson = athlType.getSelectionModel().getSelectedItem();
            logger.trace(selectedPerson.name());
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws SQLException, ClassNotFoundException, IOException {
            if (isInputValid()) {
                gamesAthlete = gamesDAL.getAthleteDAO().getNewAthlete(
                        athlName.getText(),
                        Integer.parseInt(athlAge.getText()),
                        athlState.getText(),
                        athlType.getSelectionModel().getSelectedItem().name()
                );

                okClicked = true;
                dialogStage.close();
            }
    }


    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (athlType.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "PleaseSelect sport";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
