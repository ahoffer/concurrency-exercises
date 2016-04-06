public class MyResource {

    private Integer value = 1;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public synchronized void update(Integer value, MyResource otherResource) {
        setValue(value);
        otherResource.setValue(computeNewValue(otherResource.getValue()));

    }

    private Integer computeNewValue(Integer otherValue) {


        return otherValue * value;
    }
}
