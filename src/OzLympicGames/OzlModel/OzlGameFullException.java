package OzLympicGames.OzlModel;

import OzLympicGames.GamesHelperFunctions;

/**
 * Thrown if attempting to assign a game which already has met maximum allowed players threshold
 *
 * @author dimz
 * @since 26/4/17.
 */
public class OzlGameFullException extends IllegalArgumentException {
    public OzlGameFullException(OzlGame aGame) {
        super(String.format("This game is full %s : %s",
                aGame.getId(),
                GamesHelperFunctions.firsLetterToUpper(aGame.getGameSport().toString()))
        );
    }
}
