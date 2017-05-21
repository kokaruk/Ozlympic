package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Dialogues;
import ozlympicgames.ozlmodel.GamesHelperFunctions;
import ozlympicgames.ozlmodel.GamesOfficial;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.dal.GamesDAL;
import ozlympicgames.ozlmodel.dal.IGamesDAL;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dimz
 * @since 22/5/17.
 */
public class TableRootController {

    private static Logger logger = LogManager.getLogger();
    private IGamesDAL gamesDAL;

    @FXML
    private Label activeGame;
    @FXML
    private Button addReferee;
    @FXML
    private Button addPlayers;
    @FXML
    private Button gamePlay;

    @FXML
    private void initialize() {
        showGameDetails(null);
        addReferee.setDisable(true);
        addPlayers.setDisable(true);
        gamePlay.setDisable(true);
        try{
            gamesDAL = GamesDAL.getInstance();
        } catch (Exception e) {
            logger.error(e.toString());
            Dialogues.createExceptionDialog(e);
        }

    }
    void showGameDetails(OzlGame ozlGame) {
        if (ozlGame != null) {
            activeGame.setText(ozlGame.getId() + " " + GamesHelperFunctions.firsLetterToUpper(ozlGame.getGameSport().name()));
            ozlGame.addParticipant(getReferee());
            gamesDAL.get_athletes().values().stream()
                    .filter(gamesAthlete ->
                            gamesAthlete.getAthleteType().getSport().size() == 1 &&  gamesAthlete.getAthleteType().getSport().iterator().next() == ozlGame.getGameSport() ||  gamesAthlete.getAthleteType().getSport().size() > 1 )
                    .limit(8- ozlGame.getGameAthletes().size()).forEach(gamesAthlete -> ozlGame.addParticipant(gamesAthlete));
            ozlGame.gamePlay();

            Alert alert = new Alert(Alert.AlertType.ERROR);
           // alert.initOwner(dialogStage);
            alert.setTitle("Game results");
            alert.setHeaderText("This is so hacky, its a shame :((( ");

            StringBuilder stringBuilder = new StringBuilder();

            for(String blah :ozlGame.get_referee().getGameScore(ozlGame)){
                String z = blah + "\r\n";
                stringBuilder.append(z );
            }
            alert.setContentText(stringBuilder.toString());

            alert.showAndWait();


        } else {
            activeGame.setText(null);
        }
    }

         /// this is so hacky, shame shame
        private GamesOfficial getReferee(){
            int refCount = gamesDAL.get_referees().size();
            int refs = GamesHelperFunctions.getRandomNumberInRange(0, refCount);
            List<GamesOfficial> refereeList = new ArrayList<>();
            refereeList.addAll(gamesDAL.get_referees().values());
            GamesOfficial referee = refereeList.get(refs);
            return referee;
        }
}
