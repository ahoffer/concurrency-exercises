package com.connexta;

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

    public static Class classUnderTest = Greeter.class;

    private AbstractGreeter jack, jill;

    @Before
    public void setup() throws IllegalAccessException, InstantiationException {
        jack = (AbstractGreeter) classUnderTest.newInstance();
        jill = (AbstractGreeter) classUnderTest.newInstance();
        jack.setName("Jack");
        jill.setName("Jill");
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