public class PassengerInfo { // Stores all information of a passenger
    Boolean survived; // true for survived, false for did not surive
    int ticketClass; // 1 for first class, 2 for 2nd class, 3 for 3rd class
    String sex;
    double age;
    int sibSp; // # of siblings/spouses aboard
    int parch; // # of parents/children aboard
    double fare;

    PassengerInfo(String[] values) { //Given a row in the CSV, populate the fields
        this.survived = values[0].equals("1");
        this.ticketClass = Integer.parseInt(values[1]); 
        this.sex = values[3];
        this.age = Double.parseDouble(values[4]);
        this.sibSp = Integer.parseInt(values[5]);
        this.parch = Integer.parseInt(values[6]);
        this.fare = Double.parseDouble(values[7]);
    }
}
