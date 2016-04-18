package com.connexta;

import java.util.List;

public class Greeter extends AbstractGreeter {


    //    public synchronized void greet(com.connexta.Greeter greeted, List<String> recorder) {
    //
    //        recorder.add(getMessage("greets ", greeted.name));
    //        greeted.returnGreeting(this, recorder);
    //    }


    @Override
    public void greet(AbstractGreeter greeted, List<String> recorder) {

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
