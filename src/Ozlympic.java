import OzLympicGames.OzlympicGamesMVC.OzlController.GameController;
import OzLympicGames.OzlympicGamesMVC.OzlModel.OzlGamesModel;
import OzLympicGames.OzlympicGamesMVC.OzlView.GameView;


/**
 * Created by dimz on 1/4/17.
 */
class Ozlympic {

    public static void main(String[] args) {
        GameView view = new GameView();
        view.clearScreen();
        System.out.println("\033[34mLoading...");
        // init model
        OzlGamesModel model = new OzlGamesModel();

        GameController controller = new GameController(model, view);
        controller.startGames();
    }
}

