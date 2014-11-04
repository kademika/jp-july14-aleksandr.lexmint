package day12.skatingrink;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lexmint on 06.08.14.
 */
public class Launcher {
    public static void main(String[] args) {

        System.out.println("Skating rink is opening!");
        final SchoolSkatingRink skatingRink = new SchoolSkatingRink();

        ExecutorService executorService = Executors.newFixedThreadPool(7);
        final Random random = new Random();
        for (int i = 0; i < 7000; i++) {
            final Skater skater = new Skater("Tony Hawk" + i);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Skates skates = null;
                    try {
                        skates = skatingRink.getSkates(skater);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(random.nextInt(25) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        skatingRink.returnSkates(skates, skater);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }
}
