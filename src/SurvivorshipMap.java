import java.util.ArrayList;
import java.util.HashMap;

public class SurvivorshipMap { //Class with methods used to manipulate survivorship map
    HashMap<String, Survivorship> map = new HashMap<String, Survivorship>();

    void update_map(PassengerInfo info) { // Updates survivorship hashmap given a passenger's
                                                          // information

        Boolean survival = info.survived;

        // Update hashmap's "category" keys
        if (info.ticketClass == 1) {
            update_key("1st Class", survival);

        } else if (info.ticketClass == 2) {
            update_key("2nd Class", survival);
        } else {
            update_key("3rd Class", survival);
        }

        if (info.sex.equals("male")) {
            update_key("Male", survival);
        } else {
            update_key("Female", survival);
        }

        // Update hashmap's "bin" keys
        update_key(num_get_key(info.age, "age"), survival);
        update_key(num_get_key(info.sibSp, "sibSp"), survival);
        update_key(num_get_key(info.parch, "parch"), survival);
        update_key(num_get_key(info.fare, "fare"), survival);

    }

    void update_key(String key, Boolean survived) { // Updates or adds a specific key of the survivorship hashmap
        Survivorship key_survivorship;

        if (map.keySet().contains(key)) { // Update key if already in map
            key_survivorship = map.get(key);

            if (survived) { // Only add to survivors field if person survived
                key_survivorship.survivors++;
            }

            key_survivorship.total++;
            key_survivorship.percent_survived = (int) Math
                    .round(key_survivorship.survivors / key_survivorship.total * 100);

        } else { // Create new column in map if key didn't exist prior
            if (survived) {
                key_survivorship = new Survivorship(1, 1);
            } else {
                key_survivorship = new Survivorship(1, 0);
            }
        }

        map.put(key, key_survivorship);

    }

    String num_get_key(double num, String category) { // Sorts a numerical value (like fare, number siblings,
                                                             // etc.) into a key in the survivorship hashmap. Return
                                                             // this key

        Boolean sorted = false;
        double counter = 0;
        String key = "";
        double increment = 3;

        if (category.equals("age")) { // Choose size of each bin (for example, each age key stores the aggregate data
                                      // of twenty different
                                      // ages)
            increment = 20;
        } else if (category.equals("fare")) {
            increment = 20;
        }

        while (!sorted) { // Work through bins until you find the one your number fits in
            if (num >= counter && num < counter + increment) {
                sorted = true;

                if (category.equals("age")) { // Save the key for your number (to be returned by this function)
                    key = "Age " + (int) counter + "-" + (int) (counter + increment) + " Years";
                } else if (category.equals("fare")) {
                    key = "Fare between " + (int) counter + "-" + (int) (counter + increment) + " USD";
                } else if (category.equals("sibSp")) {
                    key = (int) counter + "-" + (int) (counter + increment) + " siblings and/or spouses onboard";
                } else {
                    key = (int) counter + "-" + (int) (counter + increment) + " parents and/or children onboard";
                }
            } else {
                counter += increment;
            }
        }
        return key;
    }

    void get_top_3(String keyword) { // Prints the three key/value pairs, whose key contains a keyword that have the highest
                                            // survivorship rate
        ArrayList<String> category_keys = new ArrayList<>();
        for (String key : map.keySet()) {
            if (key.contains(keyword)) {
                category_keys.add(key);
            }
        }

        // Sort remaining keys
        String key1 = "";
        double value1 = 0;
        String key2 = "";
        double value2 = 0;
        String key3 = "";
        double value3 = 0;

        for (String key : category_keys) {
            if (map.get(key).percent_survived >= value1) {
                key3 = key2;
                value3 = value2;
                key2 = key1; // Shift previous values and keys down 1
                value2 = value1;
                key1 = key;
                value1 = map.get(key).percent_survived;
            } else if (map.get(key).percent_survived >= value2) {
                key3 = key2;
                value3 = value2;
                key2 = key;
                value2 = map.get(key).percent_survived;
            } else if (map.get(key).percent_survived >= value3) {
                key3 = key;
                value3 = map.get(key).percent_survived;
            }
        }

        // Print out top keys + survivorship
        System.out.println("    - " + key1 + ", " + map.get(key1).percent_survived + "% survival rate");
        System.out.println("    - " + key2 + ", " + map.get(key2).percent_survived + "% survival rate");
        System.out.println("    - " + key3 + ", " + map.get(key3).percent_survived + "% survival rate");

    }
}
