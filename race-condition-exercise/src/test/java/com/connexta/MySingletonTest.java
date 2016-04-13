package com.connexta;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.hamcrest.core.IsNot.not;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class MySingletonTest {

    private static final String UNKNOWN = "UNKNOWN";

    private static final Integer EXPECTED_RESULT = -298632863;

    private static final int NUMBER_Of_THREADS = 5;

    /**
     * TEST SINGLETON WITH MULTIPLE THREADS
     */
    @Test
    public void testRaceCondition() throws InterruptedException {

        List<String> objectIds = getObjectIds();
        assertThat("The singleton instance ID cannot be unknown", objectIds, not(hasItem(UNKNOWN)));

        String currentSingletonId = MySingleton.getInstance()
                .getObjectId();
        assertThat("The singleton must always have the same object ID",
                objectIds,
                everyItem(equalTo(currentSingletonId)));

        // Make sure initialization code was fully executed.
        Integer lastNumber = MySingleton.getInstance()
                .getLast();
        assertThat("Object was not initialized correctly", lastNumber, equalTo(EXPECTED_RESULT));
    }

    /**************************************************************************
     * HELPER METHODS (IGNORE)
     **************************************************************************/
    private String getObjectId(Future<MySingleton> future) {
        String objectId = UNKNOWN;
        try {
            objectId = future.get()
                    .getObjectId();
        } catch (Exception e) {
            //Do nothing
        }
        return objectId;
    }

    private List<String> getObjectIds() throws InterruptedException {

        List<Future<MySingleton>> futures = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_Of_THREADS);

        for (int i = 0; i < NUMBER_Of_THREADS; i++) {
            futures.add(executor.submit(MySingleton::getInstance));
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);

        return futures.stream()
                .map(this::getObjectId)
                .collect(Collectors.toList());

    }

    /**
     * HIDE MESSY STUFF
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        //Snag the static field "instance"
        Field field = MySingleton.class.getDeclaredField("instance");
        boolean isFinal = Modifier.isFinal(field.getModifiers());

        //If not final, it is safe to reset it.
        if (!isFinal) {
            int originalModifiers = field.getModifiers();

            //Remove final modifier if it exists
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, originalModifiers & ~Modifier.FINAL);

            //Null out the singleton instance
            field.set(null, null);

            //Reset the modifiers
            modifiersField.setInt(field, originalModifiers);

        }
    }

}