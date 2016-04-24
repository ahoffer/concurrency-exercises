package com.connexta;

import java.util.List;

public class GreeterSolution2 extends AbstractGreeter {

    @Override
    public void greet(AbstractGreeter greeted, List<String> recorder) {
        //Lock the resource needed to complete the transaction
        synchronized (greeted) {
            //Lock this resource
            synchronized (this) {
                recorder.add(getMessage(false, "greets ", greeted.getName()));
                greeted.returnGreeting(this, recorder);
            }
        }
    }

    @Override
    protected void returnGreeting(AbstractGreeter greeter, List<String> recorder) {
        recorder.add(getMessage(true, "returns the greeting", ""));

    }
}