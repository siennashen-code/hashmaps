import java.util.HashMap;
import java.util.ArrayList;

public class App {

    static Data titanic = new Data("titanic.csv");
    static HashMap<String, PassengerInfo> titanicMap = titanic.map;
    static HashMap<String, Survivorship> survivorshipMap = new HashMap<String, Survivorship>();

    public static void main(String[] args) throws Exception {
        for (String person : titanicMap.keySet()) {
            update(titanicMap.get(person));
        }

        report_statistics();
    }

    static void update(PassengerInfo info) { // Updates survivorship hashmap given a passenger's information
        Boolean survival = info.survived;
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

        update_key(num_get_key(info.age, "age"), survival);
        update_key(num_get_key(info.sibSp, "sibSp"), survival);
        update_key(num_get_key(info.parch, "parch"), survival);
        update_key(num_get_key(info.fare, "fare"), survival);

    }

    static void update_key(String key, Boolean survived) { // Updates or adds a specific key of the survivorship hashmap
        Survivorship key_survivorship;

        if (survivorshipMap.keySet().contains(key)) {
            key_survivorship = survivorshipMap.get(key);

            if (survived) {
                key_survivorship.survivors++;
            }
            key_survivorship.total++;
            key_survivorship.percent_survived = (int) Math
                    .round(key_survivorship.survivors / key_survivorship.total * 100);

        } else {
            if (survived) {
                key_survivorship = new Survivorship(1, 1);
            } else {
                key_survivorship = new Survivorship(1, 0);
            }
        }

        survivorshipMap.put(key, key_survivorship);

    }

    static String num_get_key(double num, String category) { // Sorts a number into a key in the survivorship hashmap
        Boolean sorted = false;
        double counter = 0;
        String key = "";
        double increment = 3;

        if (category.equals("age")) {
            increment = 20;
        } else if (category.equals("fare")) {
            increment = 20;
        }

        while (!sorted) {
            if (num >= counter && num < counter + increment) {
                sorted = true;
                if (category.equals("age")) {
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

    static void report_statistics() {
        System.out.println("---What Type of Person Was Most Likely to Survive?---");
        System.out.println("For each category (age, class, fare, etc.), I've identified the top three demographics with the highest survival rate");
        System.out.println("\nBIOLOGICAL SEX");
        System.out.println("    1. Female, " + survivorshipMap.get("Female").percent_survived + "% survival rate");
        System.out.println("    2. Male, " + survivorshipMap.get("Male").percent_survived + "% survival rate");

        System.out.println("\nAGE");
        get_top_3("Years");

        System.out.println("\nCLASS");
        get_top_3("Class");

        System.out.println("\nFARE");
        get_top_3("Fare");

        System.out.println("\n# OF SIBLINGS/SPOUSES ONBOARD");
        get_top_3("siblings");
        System.out.println("\n# PARENTS/CHILDREN ONBOARD");
        get_top_3("parents");

        System.out.println("\nOVERALL DEMOGRAPHICS WITH HIGHEST SURVIVORSHIP");
        get_top_3("");
    }


    static void get_top_3(String keyword) { //Returns the three keys containing a keyword that have the highest survivorship rate
        ArrayList<String> category_keys = new ArrayList<>();
        for (String key : survivorshipMap.keySet()) {
            if (key.contains(keyword)) {
                category_keys.add(key);
            }
        }

        String key1 = "";
        double value1 = 0;
        String key2 = "";
        double value2 = 0;
        String key3 = "";
        double value3 = 0;

        for (String key : category_keys) {
            if (survivorshipMap.get(key).percent_survived >= value1) {
                key3 = key2;
                value3 = value2;
                key2 = key1;
                value2 = value1;
                key1 = key;
                value1 = survivorshipMap.get(key).percent_survived;
            } else if (survivorshipMap.get(key).percent_survived >= value2) {
                key3 = key2;
                value3 = value2;
                key2 = key;
                value2 = survivorshipMap.get(key).percent_survived;
            } else if (survivorshipMap.get(key).percent_survived >= value3) {
                key3 = key;
                value3 = survivorshipMap.get(key).percent_survived;
            }
        }

        System.out.println("    1. " + key1 + ", " + survivorshipMap.get(key1).percent_survived + "% survival rate");
        System.out.println("    2. " + key2 + ", " + survivorshipMap.get(key2).percent_survived + "% survival rate");
        System.out.println("    3. " + key3 + ", " + survivorshipMap.get(key3).percent_survived + "% survival rate");

    }
}