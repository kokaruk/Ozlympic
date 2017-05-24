package ozlympicgames.ozlmodel.dal;

import javafx.collections.ObservableMap;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesOfficial;
import ozlympicgames.ozlmodel.OzlGame;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 19/5/17.
 */
public interface IGamesDAL {

    void addAthlete(GamesAthlete gamesAthlete);

    IAthleteDAO getAthleteDAO();

    IRefereeDAO getRefereeDAO();

    IGameDAO getGameDAO();

    ObservableMap<String, OzlGame> get_games();

    ObservableMap<String, GamesAthlete> get_athletes();

    ObservableMap<String, GamesOfficial> get_referees();

    void addRefereeToGame(OzlGame game, GamesOfficial official)
            throws SQLException, ClassNotFoundException, IOException;

    void addAthleteToGame(OzlGame game, GamesAthlete athlete)
            throws SQLException, ClassNotFoundException, IOException;

    void updateParticipation(OzlGame game, GamesAthlete athlete, Double result, Integer score)
            throws SQLException, ClassNotFoundException;

}
