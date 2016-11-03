/**
 * See the README.md file for instructions.
 */

package com.connexta;

import java.util.List;

public class Greeter extends AbstractGreeter {

    @Override
    public  void greet(AbstractGreeter greeted, List<String> recorder) {

        recorder.add(getMessage(false, "greets ", greeted.getName()));
        greeted.returnGreeting(this, recorder);
    }

    @Override
    protected  void returnGreeting(AbstractGreeter greeter, List<String> recorder) {
        recorder.add(getMessage(true, "returns the greeting", ""));

    }
}
