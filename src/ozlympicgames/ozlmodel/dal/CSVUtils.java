package ozlympicgames.ozlmodel.dal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Util methods to read / write CSV files
 * parseLine copy/pasted with some heavy modifications from
 * https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
 * https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/
 * replace try-catch-finally with Java 7 try-with-resources
 * Java 8 to read & write to files
 *
 * @author Dimz
 * @since 14/5/17.
 */
public class CSVUtils {
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    private CSVUtils() {
    }

    /*
        public static void main(String[] args) throws Exception {

            String csvFile = "/Users/mkyong/csv/country2.csv";

            Scanner scanner = new Scanner(new FileInputStream(csvFile));
            while (scanner.hasNext()) {
                List<String> line = parseLine(scanner.nextLine());
                System.out.println("Country [id= " + line.get(0) + ", code= " + line.get(1) + " , name=" + line.get(2) + "]");
            }
            scanner.close();

        }
    */
    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {

        List<String> result = new ArrayList<>();

        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == customQuote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separators) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

    static void writeLine(List<String> values, String filename) throws IOException {
        try (Writer w = new FileWriter(filename, true)) {
            boolean firstVal = true;
            for (String val : values) {
                if (!firstVal) {
                    w.write(DEFAULT_SEPARATOR);
                }
                w.write(DEFAULT_QUOTE);
                for (int i = 0; i < val.length(); i++) {
                    char ch = val.charAt(i);
                    if (ch == DEFAULT_QUOTE) {
                        w.write(DEFAULT_QUOTE);  //extra quote
                    }
                    w.write(ch);
                }
                w.write(DEFAULT_QUOTE);
                firstVal = false;
            }
            w.write("\r\n");
        }
    }

    static boolean findBrokenID(String CSV_PATH, String BD_ERR_ID){
        // check if csv file has unpopulated entries, and fix
        Path path = Paths.get(CSV_PATH);
        try (Stream<String> lines = Files.lines(path)) {
            return lines.anyMatch(s -> s.contains(BD_ERR_ID));
        } catch (IOException ex) {
            // do nothing
        }
        return true;
    }

    static void fixBrokenID(String CSV_PATH, String BD_ERR_ID, String TABLE_NAME, String COLUMN_NAMES) throws IOException
    {
        Path path = Paths.get(CSV_PATH);
        List<String> lines = Files.readAllLines(path);
        try (PrintWriter output = new PrintWriter(CSV_PATH, "UTF-8")) {
            lines.stream()
                    .filter(Objects::nonNull)
                    .forEachOrdered(s -> {
                        if (s.contains(BD_ERR_ID)) {
                            List<String> SCVline = parseLine(s);

                            String paramsVals = SCVline.subList(1, SCVline.size())
                                    .stream()
                                    .collect(Collectors.joining(","));

                            try{
                                Integer idNum = ConnectionFactory.insertStatement(TABLE_NAME, COLUMN_NAMES, paramsVals);
                                String ID = String.format("%s%04d",SCVline.get(SCVline.size()-1).substring(0,2).toUpperCase(),
                                        idNum);
                                s = s.replace(BD_ERR_ID, ID);
                            } catch (SQLException | ClassNotFoundException e) {

                                s = "";
                            }
                        }
                    output.println(s);
                    });
        }
    }



}
