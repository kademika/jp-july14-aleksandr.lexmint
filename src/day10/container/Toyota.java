package day10.container;

/**
 * Created by lexmint on 24.06.14.
 */
public class Toyota extends Car {

    private int guarantee;

    public Toyota() {

    }

    public Toyota(int passengers, int speed, int price) {
        super(passengers, speed, price);
    }

    public int getGuarantee() {
        return this.guarantee;
    }
}
