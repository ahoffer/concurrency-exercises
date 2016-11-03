package com.connexta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        List<String> recorder = new ArrayList<>();
        for (int i = 0; i < REPETITIONS; i++) {
            executor.execute(() -> jack.greet(jill, recorder));
            executor.execute(() -> jill.greet(jack, recorder));
        }

        executor.shutdown();
        boolean allJobsCompleted = executor.awaitTermination(2, TimeUnit.SECONDS);
        assertThat("Deadlock detected", allJobsCompleted, is(true));

        printRecorderToConsole(recorder);

        String previousName = null, person, previousAction = null, action = null;
        for (String line : recorder) {
            String[] words = getWords(line);

            if (previousName == null) {
                previousName = words[0];
                previousAction = words[1];
            } else {
                person = words[0];
                action = words[1];

                if (action.equals("returns")) {
                    assertThat("Greeter names out of order", person, is(not(previousName)));
                    assertThat("Greetings actions out of order",
                            previousAction,
                            is(not("returns")));
                }
                previousName = person;
                previousAction = action;
            }
        }
    }

    private void printRecorderToConsole(List<String> recorder) {
        System.out.println(
                "  CONVERSATION - " + DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalDateTime.now()));
        System.out.println("===========================");
        for (String each : recorder) {
            System.out.println(each);
        }
    }

    private String[] getWords(String input) {
        return input.trim()
                .split(" ");
    }
}