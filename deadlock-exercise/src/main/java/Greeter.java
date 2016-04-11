import java.util.List;

public class Greeter {

    private String name;


    public Greeter(String name) {
        this.name = name;
    }

    //    public synchronized void greet(Greeter greeted, List<String> recorder) {
    //
    //        recorder.add(getMessage("greets ", greeted.name));
    //        greeted.returnGreeting(this, recorder);
    //    }

    //A solution
    public void greet(Greeter greeted, List<String> recorder) {

        synchronized (this.getClass()) {
            recorder.add(getMessage("greets ", greeted.name));
            greeted.returnGreeting(this, recorder);
        }
    }

    //Remove the synchronized keyword to make the tests pass.
    public void returnGreeting(Greeter greeter, List<String> recorder) {

        recorder.add(getMessage("returns the greeting", ""));

    }

    private String getMessage(String action, String directObject) {
        String lastMessage = String.format("%,15d %s %s%s%n",
                System.nanoTime(),
                this.name,
                action,
                directObject);
        System.out.println(lastMessage);
        return lastMessage;
    }

}
