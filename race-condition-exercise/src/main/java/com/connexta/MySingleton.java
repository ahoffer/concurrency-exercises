/**
 * See the README.md file for instructions.
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
