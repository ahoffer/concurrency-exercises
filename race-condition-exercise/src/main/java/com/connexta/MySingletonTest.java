package com.connexta;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;

public class MySingletonTest {

    private static final int NUMBER_Of_THREADS = 5;

    public static Class<?> classUnderTest = MySingleton.class;

    /**
     * TEST SINGLETON WITH MULTIPLE THREADS
     */
    @Test
    public void testRaceCondition() throws InterruptedException {

        // Initialization routine to be invoked when a Singleton instance is created
        // TODO: I don't think I need to inject the initializer because I can cause the race condition without a Thread.sleep call.
        Callable<UUID> initializer = () -> {
            //Thread.sleep(30);
            UUID id = UUID.randomUUID();
            System.err.println(id);
            return id;
        };

        //Do not use invokeAll. It interrupts the sleeping thread during debugging.
        //List<Future<Singleton>> futures = executor.invokeAll(tasks, (long) 2, TimeUnit.SECONDS);

        // This fails to create a race condition even though it submits jobs to an Executor.
        //        List<UUID> ids = tasks.stream()
        //                .map(executor::submit)
        //                .map((f) -> extractSingletonId(f))
        //                .collect(Collectors.toList());

        // Parallel streams FTW!
        List<UUID> ids = IntStream.rangeClosed(1, NUMBER_Of_THREADS)
                .parallel()
                .mapToObj(i -> getSingletonInstance())
                .map(Singleton::getId)
                .collect(toList());

        UUID currentSingletonId = getSingletonInstance().getId();

        assertThat("Singleton ID cannot be null", currentSingletonId, not(nullValue()));

        assertThat("The singleton must always return the same instance of the object",
                ids,
                everyItem(equalTo(currentSingletonId)));

    }

    /**************************************************************************
     * HELPER METHODS (IGNORE)
     **************************************************************************/

    private Singleton getSingletonInstance() {
        Singleton singleton = null;
        try {
            Method singletonGetter = classUnderTest.getDeclaredMethod("getInstance");
            singleton = (Singleton) singletonGetter.invoke(null, null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            fail("Internal test error");

        }

        return singleton;
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
            field.setAccessible(true);
            field.set(null, null);

            //Reset the modifiers
            modifiersField.setInt(field, originalModifiers);

        }
    }

}