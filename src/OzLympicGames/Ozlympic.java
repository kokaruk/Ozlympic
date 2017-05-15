package OzLympicGames;

import OzLympicGames.OzlController.OzlympicController;

/**
 * Main games class. Used to launch games app.
 * @since 12/05/17
 * @author dimz
 */
class Ozlympic {

    public static void main(String[] args) {
        OzlympicController controller = new OzlympicController();
        controller.addAthlete();
    }
}

