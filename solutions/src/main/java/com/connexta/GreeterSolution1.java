package com.connexta;

import java.util.List;

public class GreeterSolution1 extends AbstractGreeter {


    @Override
    public synchronized void greet(AbstractGreeter greeted, List<String> recorder) {

        //BEST SOLUTION
        synchronized (this.getClass()) {
            recorder.add(getMessage(false, "greets ", greeted.getName()));
            greeted.returnGreeting(this, recorder);
        }
    }

    @Override
    public void returnGreeting(AbstractGreeter greeter, List<String> recorder) {
        recorder.add(getMessage(true, "returns the greeting", ""));

    }
}
