package OzLympicGames.OzlympicGamesMVC.OzlModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


/**
 *
 * Created by dimi on 12/3/17.
 * abstract shared funcions between classes
 * class is final and cannot be instantiated
 */
final class GamesSharedFunctions {
    // constructor is private
    private GamesSharedFunctions() {}

    // string array
    private static ArrayList<String> randomNames = new ArrayList<String>(
            Arrays.asList("Abdul Axelrod", "Maya Matthies", "Toshia Tinker", "Jamee Jesus", "Laurette Lesh", "Ebony Exley", "Myesha Marcin", "Tyisha Tallmadge", "Markita Mccallister", "Eboni Engles", "Boyce Bitton", "Stasia Scarberry", "Christine Creger", "Argelia Asmussen", "Alesha Aquilar", "Dominque Deeds", "Maryann Muldowney", "Becky Betancourt", "Connie Carta", "Jerlene Jobst", "Sondra Sedillo", "Rey Riggs", "Celine Cormack", "Davina Desouza", "Aurelio Abadie", "Rueben Ricco", "Breana Bratt", "Augustina Amsden", "Chau Choi", "Wilma Whitefield", "Iola Ivory", "Elnora Eggebrecht", "Juliana Jenner", "Chery Chunn", "Heather Hammell", "Shandi Striegel", "Tamiko Tellier", "Chauncey Cai", "Sung Spiers", "Lala Lacefield", "Julee Journey", "Tova Tibbetts", "Morton Maass", "Willena Weeden", "Lashunda Lippold", "Charmaine Carter", "Elba Ellefson", "Ginette Goodwill", "Elicia Elsey", "Olimpia Oshea", "Natividad Nyquist", "Ceola Chmiel", "Kimbra Kirkwood", "Almeta Argo", "Marshall Mcclintock", "Madlyn Morais", "Jamika Jarmon", "Camelia Clavette", "Nathalie Niday", "Alexandria Alvarenga", "Vernie Veal", "Landon Lesure", "Maple Millward", "Kacey Kealoha", "Andera Audia", "Samual Sandell", "Mitzie Markle", "Shu Summy", "Karey Kerlin", "Odis Oconner", "Kylee Khang", "Cameron Corby", "Vanita Vitale", "Demetrice Donahue", "Gayla Greaney", "Marisha Mallory", "Toni Tindle", "Freda Federico", "Mandie Melgarejo", "Darcie Danzy", "Lennie Loney", "Ione Ingham", "Vicki Viars", "Azucena Albright", "Russel Rocco", "Maynard Mitchel", "Mose Mccaul", "Alycia Atencio", "Alejandrina Altizer", "Marylouise Marcinkowski", "Dina Difranco", "Lelia Lieb", "Houston Hedlund", "Cary Condon", "Frederick Fung", "Roselee Ronning", "Ilene Iliff", "Sherice Soliman", "Elmira Ethridge", "Pamila Penman", "Janis Juares", "Florence Frisch", "Eloisa Elmer", "Honey Howton", "Wayne Weld", "Chadwick Chitwood", "Marylou Matthes", "Iva Isherwood", "Janell Joines", "Lisha Legge", "Eugene Edenfield", "Dominga Dunnington", "Yuk Yamashita", "Odelia Oakman", "Marti Mccune", "Sheri Schlosser", "Yvette Yorke", "Jackie Jacox", "Tonie Truitt", "Wilhemina Wedge", "Ken Krohn", "Tomeka Tores", "Mallory Minter", "Sau Sisson", "Abraham Asbury", "Phillis Polo", "Nicky Northcott", "Robin Rockey", "Pamala Pardo", "Tiffaney Tsosie", "Edison Elks", "Arianna Arena", "Juana Jeppson", "Babette Biel", "Camie Custis", "Kimberlee Kunze", "Gina Giefer", "Eliana Eads", "Sade Schurg", "Melinda Moraga", "Criselda Connolly", "Florentino Figeroa", "Charlotte Coldwell", "Sol Schmidtke", "Merle Mansfield", "Darby Dumlao", "Ji July", "Genie Gonzalez", "Louise Lustig", "Keisha Kibbe")
    );


    //method to return a random name
    public static String getRandomName(){
        int randomArrayIndex = getRandomNumberInRange(0, randomNames.size());
        return randomNames.get(randomArrayIndex);
    }


    static int getRandomNumberInRange(int min, int max) {

        Random myRandomNumber = new Random();
        return myRandomNumber.ints(min, (max + 1)).findFirst().getAsInt();

    }

}

