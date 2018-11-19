package uff.simonalzheimer.app;

/**
 * Created by Juan Lucas Vieira on 19/11/2018.
 */

public class Condition<X,Y> {

    private X key;
    private Y value;

    public Condition(X key, Y value) {
        this.key = key;
        this.value = value;
    }

    public X getKey() {
        return key;
    }

    public void setKey(X key) {
        this.key = key;
    }

    public Y getValue() {
        return value;
    }

    public void setValue(Y value) {
        this.value = value;
    }
}
