package day12.stargate;

import java.awt.*;

/**
 * Created by lexmint on 26.07.14.
 */
public class Ship implements Runnable {
    private int x;
    private int y;
    private int height;
    private int width;

    public Ship(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.drawOval(x, y, width, height);
        g.fillOval(x, y, width, height);
    }

    @Override
    public void run() {
        synchronized (Launcher.ship) {
            while (x != 480) {
                x++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Gate: We've arrived, waiting for the gate");
            synchronized (Launcher.gate) {
                Launcher.gate.notify();
            }
            try {
                Launcher.ship.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (x != 900) {
                x++;
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Ship: Moving through the gates");
            synchronized (Launcher.gate) {
                System.out.println("Ship: We've passed through, close the gates");
                Launcher.gate.notify();
            }
            Launcher.shipPassed();

        }
    }
}
