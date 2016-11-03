##Instructions
  <p>
The singleton pattern is a design pattern that restricts the instantiation of a class to a single object. This is useful when exactly one object is needed to coordinate actions across the system [Wikipedia].
  <p>
Implemetinng a singleton in multithreaded environments presents a challenge. The programmer must be careful to avoid race conditions when creating singletons. Multiple threads could create multiple instances of the singleton class.
  <p>
In this exercise, the class <b>MySingleton</b> is meant to be a singleton, but it does not protect against race conditions. The unit tests for this class attempt to cause a race conditions. Run the unit test and see that they fail.
  <p>
The goal of the exercise is to modify the MySingleton class so it passes the unit tests. Do not modify the unit tests or classes other than MySingleton.
