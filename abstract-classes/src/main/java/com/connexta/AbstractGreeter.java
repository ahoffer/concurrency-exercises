package com.connexta;

import java.util.List;

public abstract class AbstractGreeter {

    private String name;

    public abstract void greet(AbstractGreeter greeted, List<String> recorder);

    public abstract void returnGreeting(AbstractGreeter greeter, List<String> recorder);

    protected String getMessage(boolean indent, String action, String directObject) {
        String lastMessage = String.format("%s%s %s%s%n",
                indent ? "\t" : "",
                this.getName(),
                action,
                directObject);
        System.out.print(lastMessage);
        return lastMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
