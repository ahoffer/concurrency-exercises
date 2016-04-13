## Example Solutions to race-condition-exercise
The question asks to solve the problem of how to create a singleton instance in a multithreaded environment.

If multiple threads access the constructor simultaneously, additional instances could be created between the time it takes to initialize an object and assign it to a variable.

```java
 public static MySingleton getInstance() {

    /*  Thread 2 comes along after thread 1. The value of 'instance'
        is still null. Thread 2 blows past the null check and creates
        a new instance.
    */

    if (instance == null) {
        /*  Thread 1 enters the method first. After passing the null
             check, it executes is executing the MySingleton constructor,
             but hasn't returned a value yet.
         */
            instance = new MySingleton();
        }

    return instance;
}
```

The constructor calls an initialization method:

```java
private MySingleton() {
        initialize();
    }
```

The initialization methods is deliberately long running to increase the change that multithreads will create multiple instances of the singleton.

### Solution 1: synchronization

A simple solution is to add the key word `syncrhonized` to the `getInstance` method signature.

```java
public synchronized static MySingleton getInstance() {
```

The synchronization keyword only allows one thread at a time to enter the method.

### Solution 2: static initialization

Another solution is to use a `static final` initializer to create the singleton instance.

```java
    static final MySingleton instance = new MySingleton();
```

The method `getInstance` then reduces to

```java
  public static MySingleton getInstance() {

        return instance;
    }
```

This is an elegant solution and is the least susceptible to race conditions. However, the instance can only be created once, when the class is loaded into the Java Runtime Environment. It might not be the appropriate solution in every cases.