package com.connexta;

public class MySingletonSolution1 extends AbstractSingleton {

    /**
     * The singleton variable
     */
    static MySingletonSolution1 instance = null;

    /**
     * Private constructor to prevent creation of new instances
     */
    private MySingletonSolution1() {
        initialize();
    }

    /**
     * Get the singleton instance.
     */
    public synchronized static MySingletonSolution1 getInstance() {
        if (instance == null) {
            instance = new MySingletonSolution1();
        }

        return instance;
    }

}
