import java.util.HashMap;

public class App {

    static Data titanic = new Data("titanic.csv");
    static HashMap<String, PassengerInfo> titanicMap = titanic.map;
    static HashMap<String, Survivorship> survivorshipMap = new HashMap<String, Survivorship>();

    public static void main(String[] args) throws Exception {
        for (String person : titanicMap.keySet()) {
            update(titanicMap.get(person));
        }

        for (String key : survivorshipMap.keySet()) {
            Survivorship survivorship = survivorshipMap.get(key);
            int percent_survived = (int) Math.round(survivorship.survivors/survivorship.total*100);
            System.out.println(key + ": " + percent_survived + "% survival rate");
        }
    }

    static void update(PassengerInfo info) { //Updates survivorship hashmap given a passenger's information
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

    static void update_key(String key, Boolean survived) { //Updates or adds a specific key of the survivorship hashmap
        Survivorship key_survivorship;

        if (survivorshipMap.keySet().contains(key)) {
            key_survivorship = survivorshipMap.get(key);
            
            if (survived) {
                key_survivorship.survivors++;
            }
            key_survivorship.total++;
            key_survivorship.percent_survived = (int) Math.round(key_survivorship.survivors/key_survivorship.total*100);

        } else {
            if (survived) {
                key_survivorship = new Survivorship(1, 1);
            } else {
                key_survivorship = new Survivorship(1, 0);
            }
        }

        survivorshipMap.put(key, key_survivorship);

    }

    static String num_get_key(double num, String category) { //Sorts a number into a key in the survivorship hashmap
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
                    key = "Age " + (int)counter + "-" + (int)(counter + increment) + " Years";
                } else if (category.equals("fare")) {
                    key = "Fare between " + (int)counter + "-" + (int)(counter + increment) + " USD";
                } else if (category.equals("sibSp")){
                    key = (int)counter + "-" + (int)(counter + increment) + " siblings and/or spouses onboard";
                } else {
                    key = (int)counter + "-" + (int)(counter + increment) + " parents and/or children onboard";
                }
            } else {
                counter += increment;
            }
        }
        return key;
    }

    static void report_statistics(){ 
        System.out.println("---What Type of Person Was Most Likely to Survive?---");

        System.out.println("Sex");
        System.out.println("Female, ");
        System.out.println("Age");
        
        System.out.println("Class");
        System.out.println("Fare");
        
        System.out.println("Siblings/Spouses Onboard");
        System.out.println("Parents/Children Aboard");
    }

}
