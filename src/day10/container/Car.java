package day10.container;

/**
 * Created by lexmint on 24.06.14.
 */
public abstract class Car {
    protected int passengers;
    protected int speed;
    protected int price;

    public Car() {

    }

    public Car(int passengers, int speed, int price) {
        this.passengers = passengers;
        this.speed = speed;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    protected String model;

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
