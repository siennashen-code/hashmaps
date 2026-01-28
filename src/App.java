import java.util.HashMap;

public class App {
    static Data titanic = new Data("titanic.csv");
    static HashMap<String, PassengerInfo> titanicMap = titanic.map;
    static SurvivorshipMap survivorshipMap = new SurvivorshipMap();

    public static void main(String[] args) throws Exception {
        for (String person : titanicMap.keySet()) { // Work through all passengers, updating survivorship map
            survivorshipMap.update_map(titanicMap.get(person)); 
        }

        report_statistics();
    }

    static void report_statistics() {
        System.out.println("---What Type of Person Was Most Likely to Survive the Titanic Sinking?---");
        System.out.println(
                "For each category (age, class, fare, etc.), I've identified the top three demographics with the highest survival rate");

        System.out.println("\nBIOLOGICAL SEX");
        System.out.println("    1. Female, " + survivorshipMap.map.get("Female").percent_survived + "% survival rate");
        System.out.println("    2. Male, " + survivorshipMap.map.get("Male").percent_survived + "% survival rate");

        System.out.println("\nAGE");
        survivorshipMap.get_top_3("Years");

        System.out.println("\nCLASS");
        survivorshipMap.get_top_3("Class");

        System.out.println("\nFARE");
        survivorshipMap.get_top_3("Fare");

        System.out.println("\n# OF SIBLINGS/SPOUSES ONBOARD");
        survivorshipMap.get_top_3("siblings");
        System.out.println("\n# PARENTS/CHILDREN ONBOARD");
        survivorshipMap.get_top_3("parents");

        System.out.println("\nOVERALL DEMOGRAPHICS WITH HIGHEST SURVIVORSHIP");
        survivorshipMap.get_top_3("");
    }

    
}
