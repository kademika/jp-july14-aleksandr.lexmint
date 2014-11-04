package day12.stargate;

/**
 * Created by lexmint on 26.07.14.
 */
public class Launcher {
    public static volatile Gate gate;
    public static volatile Ship ship;

    public static void main(String[] args) throws InterruptedException {

        gate = new Gate(500, 10, 30, 30);
        new Thread(gate).start();


        ship = new Ship(10, 10, 10, 10);
        new Thread(ship).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                UI ui = new UI(ship, gate);
                while(true) {

                    ui.repaint();
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    public static void shipPassed() {
        ship = new Ship(10, 10, 10, 10);
        new Thread(ship).start();
    }
}
