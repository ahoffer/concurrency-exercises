##Instructions

Deadlock is a situation in which two or more threads
are each waiting for the other to finish, and thus neither ever does.

This exercise creates a deadlock between two <b>Greeters</b>.
A greeter can only do two things: greet someone and return a
greeting.

Running the unit tests will reault in failure because of the
deadlock. The goal of the exercise is to modify the <b>Greeter</b>
class to resolve the deadlock and pass the unit tests.

Do not modify the unit tests or any classes other than <b>Greeter</b>