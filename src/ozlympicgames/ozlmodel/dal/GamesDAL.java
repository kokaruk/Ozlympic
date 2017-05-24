package ozlympicgames.ozlmodel.dal;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ozlympicgames.ozlmodel.GamesAthlete;
import ozlympicgames.ozlmodel.GamesOfficial;
import ozlympicgames.ozlmodel.OzlGame;
import ozlympicgames.ozlmodel.OzlParticipation;

import javax.sql.rowset.CachedRowSet;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author dimz
 * @since 19/5/17.
 */
public class GamesDAL implements IGamesDAL {

    private static IAthleteDAO athleteDAO;
    private static IRefereeDAO refereeDAO;
    private static IGameDAO gameDAO;

    // singleton instance
    private static IGamesDAL instance;

    static {
        // init all DAO objects
        athleteDAO = AthleteDAO.getInstance();
        refereeDAO = RefereeDAO.getInstance();
        gameDAO = GameDAO.getInstance();
        // init associations

    }

    // global collections
    private ObservableMap<String, OzlGame> _games;
    private ObservableMap<String, GamesOfficial> _referees;
    private ObservableMap<String, GamesAthlete> _athlets;
    private ObservableList<OzlParticipation> _participation;

    // private constructor
    private GamesDAL() throws SQLException, ClassNotFoundException, IOException {
        _games = FXCollections.observableMap(gameDAO.getGamesMap());
        _referees = FXCollections.observableMap(refereeDAO.getOfficialsMap());
        _athlets = FXCollections.observableMap(athleteDAO.getAthletesMap());
        restoreRefereeParticipation();
        restoreParticipation();
    }

    // lazy instantiation
    public static IGamesDAL getInstance() throws SQLException, ClassNotFoundException, IOException {
        if (instance == null) {
            instance = new GamesDAL();
        }
        return instance;
    }

    @Override
    public ObservableMap<String, OzlGame> get_games() {
        return _games;
    }

    @Override
    public ObservableMap<String, GamesAthlete> get_athletes() {
        return _athlets;
    }

    @Override
    public ObservableMap<String, GamesOfficial> get_referees() {
        return _referees;
    }

    @Override
    public void addAthlete(GamesAthlete gamesAthlete){
        this._athlets.put(gamesAthlete.getId(), gamesAthlete);
    }

    @Override
    public IAthleteDAO getAthleteDAO() {
        return athleteDAO;
    }

    @Override
    public IRefereeDAO getRefereeDAO() {
        return refereeDAO;
    }

    @Override
    public IGameDAO getGameDAO() {
        return gameDAO;
    }



    private void restoreRefereeParticipation() throws SQLException, ClassNotFoundException {
        SQLPreBuilder refereeLookupPrebuilder = new SQLPreBuilder("GAMEREFEREE");
        CachedRowSet rs = refereeLookupPrebuilder.getRowSetFromView("", "");
        if (rs != null) {
            while (rs.next()) {
                String gameId = rs.getString("GAME_ID");
                _games.get(gameId).addParticipant(_referees.get(rs.getString("REF_ID")));
            }
        }
    }

    private void restoreParticipation()  throws SQLException, ClassNotFoundException {
        SQLPreBuilder refereeLookupPrebuilder = new SQLPreBuilder("PARTICIPATION");

        CachedRowSet rs = refereeLookupPrebuilder.getRowSetFromView("", "");
        if (rs != null){
            while (rs.next()) {
                GamesAthlete athlete = _athlets.get(rs.getString("ATHL_ID"));
                OzlGame game = _games.get(rs.getString("GAME_ID"));

                OzlParticipation participation = new OzlParticipation(
                        athlete,
                        game,
                        rs.getInt("SCORE"),
                        rs.getDouble("RESULT")
                );

                game.addParticipant(athlete);
            }
        }


    }
    @Override
    public void addRefereeToGame(OzlGame game, GamesOfficial official)
            throws SQLException, ClassNotFoundException, IOException {
        SQLPreBuilder refereeLookupPrebuilder = new SQLPreBuilder("GAMEREFEREE");
        //check if game has referee
        if (game.get_referee() != null ){ //update
            // not implemented yet, only allows to add new referee once
        } else { // insert
            String paramsVals = Integer.parseInt(game.getId().substring(2)) + ","
                    + Integer.parseInt(official.getId().substring(2));
            String ID = refereeLookupPrebuilder.getNewIdNum(paramsVals).toString();
            refereeLookupPrebuilder.appendCSV(ID, paramsVals);
        }

    }

}
