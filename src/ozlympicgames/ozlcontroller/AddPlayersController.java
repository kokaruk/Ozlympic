package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 24/5/17.
 */
public class AddPlayersController {

    private Stage dialogStage;
    private boolean okClicked = false;


    @FXML
    private void initialize() {

    }

    @FXML
    private void handleOk() throws SQLException, ClassNotFoundException, IOException {
        if (isInputValid()) {
            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        return true;
    }

}
