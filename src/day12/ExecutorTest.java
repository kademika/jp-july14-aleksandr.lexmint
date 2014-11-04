package day12;

import java.util.concurrent.*;

/**
 * Created by lexmint on 06.08.14.
 */
public class ExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future result = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(5000);
                throw new Exception("Test");
            }
        });
        System.out.println(result.get());
        System.out.println(result.isDone());
        System.out.println(result.isDone());
    }
}
