package day12.stargate;

import java.awt.*;

/**
 * Created by lexmint on 26.07.14.
 */
public class Gate implements Runnable {
    private int x;
    private int y;
    private int height;
    private int width;

    public Gate(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(x, y, width, height);
        g.fillRect(x, y, width, height);
    }

    @Override
    public void run() {
        while(true) {
            synchronized (Launcher.gate) {
                System.out.println("Gate: Waiting for the ship");
                try {
                    Launcher.gate.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Gate: Opening the gate");
                while (this.y != 50) {
                    this.y += 1;
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Gate: Gates are opened");
                synchronized (Launcher.ship) {
                    Launcher.ship.notify();
                }
                try {
                    Launcher.gate.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Gate: Closing the gates");
                while(this.y != 10) {
                    this.y -= 1;
                    try {
                        Thread.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.x = 500;
                this.y = 10;
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
