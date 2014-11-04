package day12.ball;

import java.awt.*;
import java.util.Random;

/**
 * Created by lexmint on 25.07.14.
 */
public class Launcher  {
    public static void main(String[] args) throws InterruptedException {
        Color[] colors = new Color[] {
            Color.BLACK,
            Color.YELLOW,
            Color.DARK_GRAY,
            Color.PINK,
            Color.BLUE,
            Color.MAGENTA,
            Color.LIGHT_GRAY,
            Color.GRAY,
            Color.GREEN,
            Color.ORANGE,
            Color.PINK,
            Color.YELLOW
        };

        UI ui = new UI();

        Random r = new Random(415135901285952l);

        for (int i = 0; i < 10; i++) {
            Ball ball = new Ball(colors[r.nextInt(12)], 0, i * 60, 10, 10, r.nextInt(20), ui);
            BallThread ballThread = new BallThread(ball);
            ballThread.start();
        }

        while(true) {
            ui.update();
            Thread.sleep(16);
        }


    }
}
