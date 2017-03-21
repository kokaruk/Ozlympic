package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.ArrayList;

/**
 * Created by dimi on 10/3/17.
 */
// enumerator for athlete type
 enum AthleteType {
    swimmer(new GameSports[]{GameSports.swimming}),
    cyclist(new GameSports[]{GameSports.cycling}),
    sprinter(new GameSports[]{GameSports.running}),
    superAthlete(new GameSports[]{GameSports.cycling, GameSports.running, GameSports.swimming});

    // enum accessors
    private final GameSports[] sports;
    public GameSports[] getSport() {
        return sports;
    }

    // constructor
    AthleteType(GameSports[] sports){
        this.sports = sports;
    }
}
