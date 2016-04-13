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

        //BEST SOLUTION
        synchronized (this.getClass()) {
            recorder.add(getMessage(false, "greets ", greeted.name));
            greeted.returnGreeting(this, recorder);
        }
    }

    //Remove the synchronized keyword to make the tests pass.
    public void returnGreeting(Greeter greeter, List<String> recorder) {

        recorder.add(getMessage(true, "returns the greeting", ""));

    }

    private String getMessage(boolean indent, String action, String directObject) {
        String lastMessage = String.format("%s%s %s%s%n",
                indent ? "\t" : "",
                this.name,
                action,
                directObject);
        System.out.print(lastMessage);
        return lastMessage;
    }

}
