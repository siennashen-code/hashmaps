import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Data { //Read in CSV and store into a hashmap
    HashMap<String, PassengerInfo> map;
    String filename;

    Data(String filename) {
        this.filename = filename;
        this.map = map_CSV();
    }

    public HashMap<String, PassengerInfo> map_CSV() {
        Scanner scanner;
        HashMap<String, PassengerInfo> map = new HashMap<String, PassengerInfo>();

        try {
            scanner = new Scanner(new File(filename));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");

                if (values[0].equals("Survived")) { //The first row of CSV is the header row
                    continue; 
                }

                PassengerInfo passengerInfo = new PassengerInfo(values);
                map.put(values[2], passengerInfo);
            }
            scanner.close();
    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return map;
    }

}
