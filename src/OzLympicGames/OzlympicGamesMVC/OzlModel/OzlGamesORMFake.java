package OzLympicGames.OzlympicGamesMVC.OzlModel;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dimi on 12/3/17.
 * Fake Object Relation Mapper.
 */
final class OzlGamesORMFake implements IOzlGamesORM {


    // private constructor
    private OzlGamesORMFake() {}

    // singleton instance
    private static OzlGamesORMFake instance;
    // lazy instantiation
    static OzlGamesORMFake getInstance(){
        if(instance == null){
            instance = new OzlGamesORMFake();
        }
        return instance;
    }

    // string array of 150 random names
    private static List<String> randomNames = new LinkedList<>(
            Arrays.asList("Abdul Axelrod", "Maya Matthies", "Toshia Tinker", "Jamee Jesus", "Laurette Lesh", "Ebony Exley", "Myesha Marcin", "Tyisha Tallmadge", "Markita Mccallister", "Eboni Engles", "Boyce Bitton", "Stasia Scarberry", "Christine Creger", "Argelia Asmussen", "Alesha Aquilar", "Dominque Deeds", "Maryann Muldowney", "Becky Betancourt", "Connie Carta", "Jerlene Jobst", "Sondra Sedillo", "Rey Riggs", "Celine Cormack", "Davina Desouza", "Aurelio Abadie", "Rueben Ricco", "Breana Bratt", "Augustina Amsden", "Chau Choi", "Wilma Whitefield", "Iola Ivory", "Elnora Eggebrecht", "Juliana Jenner", "Chery Chunn", "Heather Hammell", "Shandi Striegel", "Tamiko Tellier", "Chauncey Cai", "Sung Spiers", "Lala Lacefield", "Julee Journey", "Tova Tibbetts", "Morton Maass", "Willena Weeden", "Lashunda Lippold", "Charmaine Carter", "Elba Ellefson", "Ginette Goodwill", "Elicia Elsey", "Olimpia Oshea", "Natividad Nyquist", "Ceola Chmiel", "Kimbra Kirkwood", "Almeta Argo", "Marshall Mcclintock", "Madlyn Morais", "Jamika Jarmon", "Camelia Clavette", "Nathalie Niday", "Alexandria Alvarenga", "Vernie Veal", "Landon Lesure", "Maple Millward", "Kacey Kealoha", "Andera Audia", "Samual Sandell", "Mitzie Markle", "Shu Summy", "Karey Kerlin", "Odis Oconner", "Kylee Khang", "Cameron Corby", "Vanita Vitale", "Demetrice Donahue", "Gayla Greaney", "Marisha Mallory", "Toni Tindle", "Freda Federico", "Mandie Melgarejo", "Darcie Danzy", "Lennie Loney", "Ione Ingham", "Vicki Viars", "Azucena Albright", "Russel Rocco", "Maynard Mitchel", "Mose Mccaul", "Alycia Atencio", "Alejandrina Altizer", "Marylouise Marcinkowski", "Dina Difranco", "Lelia Lieb", "Houston Hedlund", "Cary Condon", "Frederick Fung", "Roselee Ronning", "Ilene Iliff", "Sherice Soliman", "Elmira Ethridge", "Pamila Penman", "Janis Juares", "Florence Frisch", "Eloisa Elmer", "Honey Howton", "Wayne Weld", "Chadwick Chitwood", "Marylou Matthes", "Iva Isherwood", "Janell Joines", "Lisha Legge", "Eugene Edenfield", "Dominga Dunnington", "Yuk Yamashita", "Odelia Oakman", "Marti Mccune", "Sheri Schlosser", "Yvette Yorke", "Jackie Jacox", "Tonie Truitt", "Wilhemina Wedge", "Ken Krohn", "Tomeka Tores", "Mallory Minter", "Sau Sisson", "Abraham Asbury", "Phillis Polo", "Nicky Northcott", "Robin Rockey", "Pamala Pardo", "Tiffaney Tsosie", "Edison Elks", "Arianna Arena", "Juana Jeppson", "Babette Biel", "Camie Custis", "Kimberlee Kunze", "Gina Giefer", "Eliana Eads", "Sade Schurg", "Melinda Moraga", "Criselda Connolly", "Florentino Figeroa", "Charlotte Coldwell", "Sol Schmidtke", "Merle Mansfield", "Darby Dumlao", "Ji July", "Genie Gonzalez", "Louise Lustig", "Keisha Kibbe")
    );

    //method to return a random name
    private static String getRandomName(){
        int randomArrayIndex = GamesSharedFunctions.getRandomNumberInRange(0, randomNames.size()-1);
        String randomName = randomNames.get(randomArrayIndex);
        randomNames.remove(randomArrayIndex); //remove name to avoid duplicates
        return randomName;
    }

    // Method to return Random State
    private static String[] ausssieStates = new String[]{"Australian Capital Territory", "New South Wales", "Victoria", "Queensland", "South Australia", "Western Australia", "Tasmania", "Northern Territory"};
    static String getRandomState(){
        int randomArrayIndex = GamesSharedFunctions.getRandomNumberInRange(0, ausssieStates.length-1);
        return ausssieStates[randomArrayIndex];
    }

    static int getRandomAge(){
        IOzlConfigRead configReader = OzlConfigRead.getInstance();;
        int minAge = configReader.getConfigInt("minAge");
        int maxAge = configReader.getConfigInt("maxAge");
        return GamesSharedFunctions.getRandomNumberInRange(minAge, maxAge);
    }

    @Override
    public OzlGame[] getGames(){

        // some logic and extensive sql that reads from database for the initial game start,
        // if nothing found generates random 10 games
        OzlGame[] myGames = new OzlGame[1];
        myGames[0] = null;

        return myGames;
    }


    // method to generate game official
    @Override
    public GamesParticipant getGameOfficial(String participantId) {
        GamesParticipant newGameOfficial =
                new GamesOfficial(getRandomName(),
                        getRandomAge(),
                        participantId,
                        getRandomState());
        return newGameOfficial;
    }

    // method to generate game Athlete
    @Override
    public GamesParticipant getGameAthlete(){
        GamesParticipant newGameAthlete =
                new GamesAthlete(getRandomName(),
                        getRandomAge(),
                        getRandomState());
        return newGameAthlete;
    }

    private static OzlGame getOzlGame(String gameID) {
        // OzlGame
        return null;
     }
}

