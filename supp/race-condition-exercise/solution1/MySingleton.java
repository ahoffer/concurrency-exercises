package com.connexta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MySingleton {

    /**
     * The singleton variable
     */
    static MySingleton instance = null;

    /**
     * Special values the singleton contains
     */
    private List<Integer> myNumbers;

    /**
     * Unique identification for the singleton instance
     */
    private UUID myId;

    /**
     * Private constructor to prevent creation of new instances
     */
    private MySingleton() {
        initialize();
    }

    /**
     * Get the singleton instance.
     */
    public synchronized static MySingleton getInstance() {
        if (instance == null) {
            instance = new MySingleton();
        }

        return instance;
    }

    /**
     * Set the initial values
     */
    private void initialize() {
        myId = UUID.randomUUID();
        myNumbers = new ArrayList<>();
        myNumbers.add(1);
        myNumbers.add(1);
        for (int i = 2; i < 50; i++) {
            myNumbers.add(myNumbers.get(i - 1) + myNumbers.get(i - 2));
        }
    }

    public String getObjectId() {
        return myId.toString();

    }

    Integer getLast() {
        return myNumbers.get(myNumbers.size() - 1);
    }

}
