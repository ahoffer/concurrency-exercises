package com.connexta;

public class MySingletonSolution2 extends Singleton {

    /**
     * The singleton variable
     */
    static final MySingletonSolution2 instance = new MySingletonSolution2();

    /**
     * Private constructor to prevent creation of new instances
     */
    private MySingletonSolution2() {
        initialize();
    }

    /**
     * Get the singleton instance.
     */
    public static MySingletonSolution2 getInstance() {

        return instance;
    }
}
