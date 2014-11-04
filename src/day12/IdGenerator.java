package day12;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by lexmint on 06.08.14.
 */
public class IdGenerator {
    AtomicLong id = new AtomicLong(0);

    public long getNextId() {
        return id.getAndIncrement();

    }

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(2);

        final IdGenerator idGenerator = new IdGenerator();
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(idGenerator.getNextId());
                }
            }).start();
            countDownLatch.countDown();
        }
        Thread.sleep(50);

    }
}
