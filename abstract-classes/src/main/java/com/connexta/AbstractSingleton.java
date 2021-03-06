package com.connexta;

import java.util.UUID;
import java.util.concurrent.Callable;

public abstract class AbstractSingleton {

    /**
     * Unique identification for the singleton instance
     */
    protected UUID id;

    /**
     * Get the singleton instance.
     */
    @SuppressWarnings("unused")
    public static AbstractSingleton getInstance(Callable<UUID> initializer) {
        throw new RuntimeException("This method is abstract and should never be called");
    }

    /**
     * @return the object's unique ID
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