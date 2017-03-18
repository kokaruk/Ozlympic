package OzLympicGames.OzlympicGamesMVC.OzlModel;

/**
 * Created by dimi on 19/3/17.
 */

final class OzlConfigReadFakeAlwaysReturnsHardcodedValues implements IOzlConfigRead {

    private OzlConfigReadFakeAlwaysReturnsHardcodedValues(){}
    private static final OzlConfigReadFakeAlwaysReturnsHardcodedValues instance = new OzlConfigReadFakeAlwaysReturnsHardcodedValues();
    public static OzlConfigReadFakeAlwaysReturnsHardcodedValues getInstance(){
        return instance;
    }

    @Override
    public int getConfigInt(String myPropertyName) {
        return 4;
    }

    @Override
    public String getConfigString(String myPropertyName) {
        return "Australia";
    }
}