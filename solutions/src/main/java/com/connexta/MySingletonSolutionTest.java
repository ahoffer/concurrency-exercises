package com.connexta;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MySingletonSolutionTest {

    public static void main(String[] args) {

        Class[] classesUnderTest = new Class[] {MySingleton.class, MySingletonSolution1.class,
                MySingletonSolution2.class};

        for (Class each : classesUnderTest) {

            MySingletonTest.classUnderTest = each;
            Result result = JUnitCore.runClasses(MySingletonTest.class);
            for (Failure failure : result.getFailures()) {
                System.out.println("************************************************");
                System.out.println(failure.toString());

            }

            if (result.wasSuccessful()) {
                System.out.println("-------------------------------------------------");
                System.out.println("Test was successful for " + each.getSimpleName());
            }
        }
    }
}


