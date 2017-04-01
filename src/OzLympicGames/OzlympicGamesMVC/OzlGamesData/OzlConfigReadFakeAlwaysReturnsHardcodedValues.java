package OzLympicGames.OzlympicGamesMVC.OzlGamesData;

/**
 * Created by dimi on 19/3/17.
 * Fake Stub to implement tests without file-system dependency
 */
public final class OzlConfigReadFakeAlwaysReturnsHardcodedValues implements IOzlConfigRead {

    private OzlConfigReadFakeAlwaysReturnsHardcodedValues(){}
    private static final OzlConfigReadFakeAlwaysReturnsHardcodedValues instance = new OzlConfigReadFakeAlwaysReturnsHardcodedValues();
    public static IOzlConfigRead getInstance(){
        return instance;
    }

    @Override
    public int getConfigInt(String myPropertyName, String myPropFile) {
        return 4;
    }

    @Override
    public String getConfigString(String myPropertyName, String myPropFile) {
        return "Victoria";
    }
}