public class Greeter {

    private String name;

    public Greeter(String name) {
        this.name = name;
    }

    public synchronized void greet(Greeter greeter) {
        System.out.format("%s greets %s%n", this.name, greeter.name);
        greeter.returnGreeting(this);
    }

    public  void returnGreeting(Greeter greeter) {
        System.out.format("%s" + " returns the greeting%n", this.name, greeter.name);
    }

}
