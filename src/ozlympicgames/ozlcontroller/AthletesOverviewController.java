package ozlympicgames.ozlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ozlympicgames.Ozlympic;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesHelperFunctions;

import java.util.Map;

/**
 * @author dimz
 * @since 21/5/17.
 */
public class AthletesOverviewController {

    private static Logger logger = LogManager.getLogger();
    private Ozlympic mainApp;
    private AthleteEditController athleteEditController;
    private AthletesStatsTableController athletesStatsTableController;

    @FXML
    private Label athlName;
    @FXML
    private Label typeAthl;
    @FXML
    private Label athlState;
    @FXML
    private Label totalAge;
    @FXML
    private void handleNewAthlete(){
        boolean okClicked = mainApp.showPersonEditDialog();
        if (okClicked) {
         // not working
         //  athletesStatsTableController.setAthletesTable(athleteEditController.getGamesAthlete());
        }
    }

    @FXML
    private void initialize() {
        showGameDetails(null);
    }

    void showGameDetails(Map.Entry<String,GamesAthlete> entry){
        if(entry != null){
            logger.trace("Athlete Accept: " + entry.getValue().getId());
            athlName.setText ( entry.getValue().getName());
            typeAthl.setText(GamesHelperFunctions.firsLetterToUpper(entry.getValue().getAthleteType().name()) );
            athlState.setText( entry.getValue().getState() );
            totalAge.setText( entry.getValue().getAge().toString() );
        } else {
            athlName.setText(null);
            typeAthl.setText(null);
            athlState.setText(null);
            totalAge.setText(null);
        }
    }

    /*
    @FXML
    private void handleNewPerson() {

        boolean okClicked = mainApp.showPersonEditDialog();
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
        }
    }*/


    public void setMainApp(Ozlympic mainApp) {
        this.mainApp = mainApp;
    }
    private void setAthleteEditController(AthleteEditController athleteEditController){
        this.athleteEditController = athleteEditController;
    }

    public void setAthletesStatsTableController(AthletesStatsTableController athletesStatsTableController) {
        this.athletesStatsTableController = athletesStatsTableController;
    }
}
