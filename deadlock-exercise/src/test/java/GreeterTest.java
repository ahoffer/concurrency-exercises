import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class GreeterTest {

    private static final int REPETITIONS = 4;

    private Greeter jack, jill;

    @Before
    public void setup() {
        jack = new Greeter("Jack");
        jill = new Greeter("Jill");
    }

    @Test
    public void deadlock() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(REPETITIONS);

        for (int i = 0; i < REPETITIONS; i++) {
            executor.execute(() -> jack.greet(jill));
            executor.execute(() -> jill.greet(jack));
        }

        executor.shutdown();
        boolean allJobsCompleted = executor.awaitTermination(1, TimeUnit.SECONDS);
        assertThat("Deadlock detected", allJobsCompleted, is(true));
    }
}