public class Survivorship {
    double total;
    double survivors;
    int percent_survived;

    Survivorship(double total, double survivors){
        this.total = total;
        this.survivors = survivors;
        this.percent_survived = (int) Math.round(survivors/total*100);

    }
}

