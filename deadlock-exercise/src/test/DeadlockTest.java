import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class DeadlockTest {

    private static final int NUMBER_Of_THREADS = 4;

    @Test
    public void testDeadlock() throws InterruptedException {
        MyResource resourceA = new MyResource();
        MyResource resourceB = new MyResource();

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_Of_THREADS);

        for (int i = 1; i <= NUMBER_Of_THREADS; i++) {
            final Integer value = i;
            executor.execute(() -> {
                MyTransaction.updateResources(value, resourceA, resourceB);
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        Integer a = resourceA.getValue();
        Integer b = resourceB.getValue();

        int x = 8;

    }
}