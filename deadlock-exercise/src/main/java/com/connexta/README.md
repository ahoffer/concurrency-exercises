##Instructions

Deadlock is a situation in which two or more threads
are each waiting for another thread to release a resource. The threads are
suspended and because they are suspended, they can never release their 
resoureces. The result is that the threads are frozen.

This exercise creates a deadlock between two <b>Greeters</b>.
A greeter can only do two things: greet someone and return a
greeting.

Running the unit test fails because of the
deadlock. The goal of the exercise is to modify the <b>Greeter</b>
class to resolve the deadlock and pass the unit tests.

Do not modify the unit tests or any classes other than <b>Greeter</b>.