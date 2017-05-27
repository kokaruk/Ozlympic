package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.GamesOfficial;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 24/5/17.
 */
public class AddRefereeController {
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
    private Label refName;
    @FXML
    private Label refState;
    @FXML
    private Label refAge;
    private Stage dialogStage;
    private GamesOfficial gamesReferee;
    private boolean okClicked = false;

    public void populsteData(String name, String state, int age) {
        refName.setText(name);
        refState.setText(state);
        refAge.setText(Integer.toString(age));
    }

    public GamesOfficial getGamesReferee() {
        return gamesReferee;
    }

    @FXML
    private void initialize() {

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

    @FXML
    private void handleOk() throws SQLException, ClassNotFoundException, IOException {

        gamesReferee = gamesDAL.getRefereeDAO().getNewReferee(
                refName.getText(),
                Integer.parseInt(refAge.getText()),
                refState.getText()
        );

        okClicked = true;
        dialogStage.close();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }


}
