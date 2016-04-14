package com.connexta;

import java.util.UUID;
import java.util.concurrent.Callable;

public abstract class Singleton {

    /**
     * Unique identification for the singleton instance
     */
    protected UUID id;

    /**
     * Get the singleton instance.
     */
    public static Singleton getInstance(Callable<UUID> initializer) {
        throw new RuntimeException("This method is abstact and should never be called");
    }

    /**
     * @return iOf UUID. The object's unique ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set the object's unique ID
     */
    protected void initialize() {
        id = UUID.randomUUID();
    }
}