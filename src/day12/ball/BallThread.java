package day12.ball;

import java.util.Random;

/**
 * Created by lexmint on 25.07.14.
 */
public class BallThread extends Thread {

    private Ball ball;

    public BallThread(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void run() {
        Random r = new Random(41240602358l);
        while (true) {
            while (ball.getX() != 599) {
                ball.move(1, 0);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            while (ball.getX() != 0) {
                ball.move(-1, 0);
                try {
                    Thread.sleep(ball.getSpeed());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }
}
