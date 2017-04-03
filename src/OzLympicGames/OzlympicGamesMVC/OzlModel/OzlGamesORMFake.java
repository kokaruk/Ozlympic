package OzLympicGames.OzlympicGamesMVC.OzlModel;

import OzLympicGames.OzlympicGamesMVC.GamesHelperFunctions;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.IOzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.OzlConfigRead;
import OzLympicGames.OzlympicGamesMVC.OzlGamesData.configFileMissingException;

import java.util.*;

/**
 * Created by dimi on 12/3/17.
 * Fake Object Relation Mapper.
 */
final class OzlGamesORMFake implements IOzlGamesORM {

    // singleton instance
    // this is a mock object of a database read, rather be a singleton.
    private static OzlGamesORMFake instance;
    // counter for game athlete types
    private final Map<AthleteType, Integer> sportsCounterMap = new EnumMap<>(AthleteType.class);
    // list to hold all the athletes
    private final List<GamesAthlete> myGamesAthletes = new LinkedList<>();
    private final IOzlConfigRead configReader = OzlConfigRead.getInstance();
    // list of all games
    private final List<IOzlGame> myGames = new LinkedList<>();
    // Method to return Random State. Hardcoded, as its only 7 values and no worth to put in a separate file.
    private final String[] ausssieStates = new String[]{"Australian Capital Territory", "New South Wales",
            "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory"};
    // string array of random names
    // using linked list, as there will be a lot of resizing of data
    private List<String> randomNames;

    // private constructor
    private OzlGamesORMFake() {
        // read names list
        try {
            randomNames = getAllNames();
        } catch (configFileMissingException err) {
            err.getMissingFile();
            System.exit(1);
        }
        // init counter for game athletes sports types, needed for id generation
        for (AthleteType sportType : AthleteType.values()) {
            sportsCounterMap.put(sportType, sportsCounterMap.getOrDefault(sportType, 0));
        }
    }

    // lazy instantiation pattern for singleton call
    static OzlGamesORMFake getInstance() {
        if (instance == null) {
            instance = new OzlGamesORMFake();
        }
        return instance;
    }

    @Override
    public Map<AthleteType, Integer> getSportsCounterMap() {
        return sportsCounterMap;
    }

    @Override
    public List<GamesAthlete> getMyGamesAthletes() {
        return myGamesAthletes;
    }

    //method to return a random name from hardcoded values, makes sure no names repeated
    private String getRandomName() {
        //see if random names list has names, return any
        if (randomNames.size() > 0) {
            int randomArrayIndex = GamesHelperFunctions.getRandomNumberInRange(0, randomNames.size() - 1);
            String randomName = randomNames.get(randomArrayIndex);
            randomNames.remove(randomArrayIndex); //remove name to avoid duplicates
            return randomName;
        } else {   // looks like run out of names, lets repopulate
            // read names list and recursive call to self
            try {
                randomNames = getAllNames();
            } catch (configFileMissingException err) {
                err.getMissingFile();
                System.exit(1);
            }
            return getRandomName();
        }
    }

    private String getRandomState() {
        int randomArrayIndex = GamesHelperFunctions.getRandomNumberInRange(0, ausssieStates.length - 1);
        return ausssieStates[randomArrayIndex];
    }

    // random age integer for preset values
    private int getRandomAge() {
        int minAge = configReader.getConfigInt("minAge", modelPackageConfig.gamesConfig);
        int maxAge = configReader.getConfigInt("maxAge", modelPackageConfig.gamesConfig);
        return GamesHelperFunctions.getRandomNumberInRange(minAge, maxAge);
    }

    // method to generate game official, override from interface contract
    @Override
    public GamesParticipant getGameOfficial(String participantId) {
        String myRandomName = getRandomName();
        return new GamesOfficial(myRandomName, getRandomAge(), participantId, getRandomState());
    }

    // method to generate game Athlete
    @Override
    public GamesParticipant getGameAthlete() {
        String myRandomName = getRandomName();
        return (myRandomName != null) ? new GamesAthlete(myRandomName, getRandomAge(), getRandomState())
                : null;
    }


    // method to mock read of games from database, returns a list of 15 games, 5 for each sport
    @Override
    public List<IOzlGame> getGames() {
        for (int i = 1; i <= 5; i++) {
            for (GameSports sport : GameSports.values()) {
                // instantiate game with id Sport letter + Number
                OzlGame myOzlGame = new OzlGame(String.format("%s%03d", Character.toUpperCase(sport.name().charAt(0)), i));
                myOzlGame.setGameParticipants(getOfficialAndAthleteArray(myOzlGame));
                // make all games to play
                myOzlGame.gamePlayGetResults();
                myGames.add(myOzlGame);
            }
        }
        return myGames;
    }

    // build array of official and participant
    @Override
    public GamesParticipant[] getOfficialAndAthleteArray(OzlGame myOzlGame) {
        int gameParticipantsBounds = myOzlGame.getGameParticipants().length;
        GamesParticipant[] myParticipants = new GamesParticipant[gameParticipantsBounds];
        // add new official to game and vice<>versa
        myParticipants[0] = getGameOfficial(String.format("REF%03d", myGames.size() + 1));
        myParticipants[0].setMyOzlGame(myOzlGame);
        // create athlete, add to myGame, add myGame to athlete
        for (int ii = 1; ii < myParticipants.length; ii++) {
            GamesParticipant athlete = getMyNewAthlete(myOzlGame);
            if (athlete == null) break; //break the loop, out of names list
            myParticipants[ii] = athlete;
            myParticipants[ii].setMyOzlGame(myOzlGame);
        }
        return myParticipants;
    }

    // method to generate a random Athlete type
    // if type is different from required for the game, simply add to the pull
    // recursively calls for self
    public GamesParticipant getMyNewAthlete(OzlGame myOzlGame) {
        //  new athlete variable
        GamesAthlete newAthlete;
        //see if can get required athlete from list of available athletes
        newAthlete = myGamesAthletes.stream()
                .filter(s -> s.getAthleteType().getSport()
                        .contains(myOzlGame.getGameSportType()))
                .filter(s -> s.getMyOzlGame() == null)
                .findFirst().orElse(null);
        // if nothing found
        if (newAthlete == null) {
            // create new athlete
            newAthlete = (GamesAthlete) getGameAthlete();
            // increment type counter
            assert newAthlete != null;
            sportsCounterMap.put(newAthlete.getAthleteType(),
                    (sportsCounterMap.getOrDefault(newAthlete.getAthleteType(), 0) + 1));
            // set id based on sport type and counter
            newAthlete.setParticipantId(String.format("%s%03d",
                    newAthlete.getAthleteType().name().substring(0, 3).toUpperCase(),
                    sportsCounterMap.get(newAthlete.getAthleteType())));
            // add athlete to pull of athletes
            myGamesAthletes.add(newAthlete);

            // check if sport type is what we need
            // fits for game requirement, i.e. superAthlete or game sport type athlete,
            // if not, recursively call this method
            // not very safe as may potentially run out of names but should work
            if (!((newAthlete.getAthleteType().getSport().size() > 1) ||
                    (newAthlete.getAthleteType().getSport().contains(myOzlGame.getGameSportType())))) {
                return getMyNewAthlete(myOzlGame);
            }
        }
        return newAthlete;
    }

    // private method to read string of fake names
    private LinkedList<String> getAllNames() throws configFileMissingException {
        return new LinkedList<>(Arrays.asList(
                configReader.getConfigString("names", "names.properties")
                        .split(","))
        );

    }
}

