/**
 * INSTRUCTIONS
 * <p>
 * The singleton pattern is a design pattern that restricts the instantiation of a class to one
 * object. This is useful when exactly one object is needed to coordinate actions across the
 * system [Wikipedia].
 * <p>
 * This goal of this exercise is to modify this class so that it pass the automated unit tests.
 * The unit tests include multiple threads trying to get the instance. By default the test
 * fail because multiple threads are able to create difference instances, when only one should
 * be created.
 */

package com.connexta;

public class MySingleton extends AbstractSingleton {

    /**
     * The singleton variable
     */
    private static MySingleton instance = null;

    /**
     * Private constructor to prevent creation of new instances
     */
    // TODO: Maybe this method can be pulled up?
    private MySingleton() {
        initialize();
    }

    /**
     * Get the singleton instance.
     */
    @SuppressWarnings("unused")
    public static MySingleton getInstance() {

        if (instance == null) {
            instance = new MySingleton();
        }
        return instance;
    }

}
