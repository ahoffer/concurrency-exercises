package com.connexta;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTest {

    public static void main(String[] args) {

        Map<Class, Class[]> testSchedule = new HashMap<>();

        // Populate the test classes and the classes under test. ==
        testSchedule.put(MySingletonTest.class,
                new Class[] {MySingleton.class, MySingletonSolution1.class,
                        MySingletonSolution2.class});

        // For each test class, run it with a different classUnderTest
        for (Map.Entry<Class, Class[]> pair : testSchedule.entrySet()) {
            for (Class classUnderTest : pair.getValue()) {
                Class testClass = pair.getKey();
                setClassUnderTest(testClass, classUnderTest);
                runTest(testClass, classUnderTest);
            }
        }
    }

    public static void runTest(Class testClass, Class classUnderTest) {
        Result result = JUnitCore.runClasses(testClass);
        for (Failure failure : result.getFailures()) {
            System.out.println("==================================================");
            System.out.println("Test faiLed for " + classUnderTest.getSimpleName());
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful()) {
            System.out.println("-------------------------------------------------");
            System.out.println("Test was successful for " + classUnderTest.getSimpleName());
        }
    }

    private static void setClassUnderTest(Class testClass, Class classUnderTest) {
        try {
            Field field = testClass.getDeclaredField("classUnderTest");
            Object oldValue = field.get(testClass);
            field.set(oldValue, classUnderTest);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


