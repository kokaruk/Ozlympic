package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.CSVUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author dimz
 * @since 25/5/17.
 */
public class GamePlayController {

    @FXML
    private Label activeGameName;
    @FXML
    private Label activeRefereeName;
    @FXML
    private Label champ1;
    @FXML
    private Label champ2;
    @FXML
    private Label champ3;

    private static Logger logger = LogManager.getLogger();

    private OzlGame activeGame;
    private Stage dialogStage;
    private boolean okClicked = false;
    private Ozlympic mainApp;

    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
    }

    public void setActiveGame(OzlGame activeGame) {
        this.activeGame = activeGame;
        showGameDetails(activeGame);
    }

    @FXML
    private void initialize() {
        showGameDetails(null);
    }

    void showGameDetails(OzlGame entry) {
        if (entry != null) {
            activeGameName.setText( GamesHelperFunctions.firsLetterToUpper(entry.getGameSport().name()));
            String refName = entry.get_referee().getName();
            activeRefereeName.setText("Referee: " + refName) ;
        } else {
            activeGameName.setText(null);
            activeRefereeName.setText(null);
            champ1.setText(null);
            champ2.setText(null);
            champ3.setText(null);
        }
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
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    private boolean isInputValid() {
        return true;
    }

    @FXML
    private void handleGameStart(){
        logger.trace("Game Start");
        OzlGame myGame = mainApp.get_games().stream().filter(ozlgame -> ozlgame == activeGame).findFirst().orElseThrow(IllegalArgumentException::new);
        myGame.gamePlay();
        myGame.setTimeRun(new Timestamp(System.currentTimeMillis()));
        List<String> result = CSVUtils.parseLine(activeGame.get_referee().getGameScore(activeGame).get(0));
        champ1.setText(String.format("1: %s | Result: %s", result.get(1), result.get(3) ));
        List<String> result2 = CSVUtils.parseLine(activeGame.get_referee().getGameScore(activeGame).get(1));
        champ2.setText(String.format("2: %s | Result: %s", result2.get(1), result2.get(3) ));
        List<String> result3 = CSVUtils.parseLine(activeGame.get_referee().getGameScore(activeGame).get(2));
        champ3.setText(String.format("3: %s | Result: %s", result3.get(1), result3.get(3) ));

        try {
            mainApp.getGamesDAL().getGameDAO().updateGame(myGame, new Timestamp(System.currentTimeMillis()).toString());
            myGame.getParticipation().forEach( ozlParticipation -> {
                        try {
                            mainApp.getGamesDAL().updateParticipation(myGame, ozlParticipation.getGamesAthlete(), ozlParticipation.getResult(), ozlParticipation.getScore());
                            mainApp.getGamesDAL().getAthleteDAO().updateAthlete(ozlParticipation.getGamesAthlete(), ozlParticipation.getGamesAthlete().getTotalPoints().toString());
                        } catch (Exception e) {
                            // do nothig
                            logger.error(e.getMessage());
                        }
                    }
            );
        } catch (Exception e) {
            // it's not critical to show an alert
            logger.warn(e.getMessage());
        }

    }


}
