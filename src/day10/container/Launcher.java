package day10.container;

/**
 * Created by lexmint on 24.06.14.
 */
public class Launcher {
    public static void main(String[] args) {
        CarBox box = new CarBox();
        box.add(new Toyota(5, 5, 5));
        box.add(new Nissan(5, 5, 5));


    }

}
