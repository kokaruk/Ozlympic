package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.ozlmodel.OzlGame;

import java.util.Map;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class GamesOverviewController {
    private TableRootController tableRootController;
    private static Logger logger = LogManager.getLogger();

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

    OzlGame ozlGame;

    @FXML
    private void initialize() {
        activateGame.setDisable(true);
        showGameDetails(null);
    }

    public void setTableRootController(TableRootController tableRootController) {
        this.tableRootController = tableRootController;
    }

    void showGameDetails(Map.Entry<String,OzlGame> entry){
        if(entry != null){
            this.ozlGame = entry.getValue();
            logger.trace("Game Accept: " + entry.getValue().getId());
            refName.setText ( entry.getValue().get_referee() != null ? entry.getValue().get_referee().getName() : null);
            gameAthletes.setText( entry.getValue().getParticipation().size() > 0 ? "" + entry.getValue().getParticipation().size() : null);
            gameWinner.setText( entry.getValue().isGamePlayed() ? entry.getValue().getGameAthletesWinnersSortedByTime().get(0).getName() : null   );
            activateGame.setDisable(false);

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



}
