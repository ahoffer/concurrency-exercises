import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

public class GreeterTest {

    private static final int REPETITIONS = 4;

    private static final String JACK = "Jack";

    private static final String JILL = "Jill";

    private Greeter jack, jill;

    @Before
    public void setup() {
        jack = new Greeter(JACK);
        jill = new Greeter(JILL);
    }

    @Test
    public void deadlock() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(REPETITIONS);
        List<String> recorder = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < REPETITIONS; i++) {
            executor.execute(() -> jack.greet(jill, recorder));
            executor.execute(() -> jill.greet(jack, recorder));
        }

        executor.shutdown();
        boolean allJobsCompleted = executor.awaitTermination(5, TimeUnit.SECONDS);
        assertThat("Deadlock detected", allJobsCompleted, is(true));

    }
}