/**
 * Created by aaronhoffer on 4/6/16.
 */
public class MyTransaction {

    public static void updateResources(Integer value, MyResource resourceA, MyResource resourceB) {
        resourceA.update(value, resourceB);

        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resourceB.update(resourceB.getValue(), resourceA);
    }

}
