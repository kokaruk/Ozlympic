package OzLympicGames.OzlympicGamesMVC.OzlView;

import java.util.Map;

/**
 * Created by dimz on 1/4/17.
 */
public interface IMenu {

    Map<Integer, IMenu> getMySubmenus();
    String getMyContent();

}
