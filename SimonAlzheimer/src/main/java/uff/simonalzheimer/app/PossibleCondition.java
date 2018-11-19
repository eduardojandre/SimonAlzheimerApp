package uff.simonalzheimer.app;

import java.util.ArrayList;

/**
 * Created by Juan Lucas Vieira on 19/11/2018.
 */

public class PossibleCondition {

    private String key;
    private ArrayList<String> values;

    public PossibleCondition(String key) {
        this.key = key;
        this.values = new ArrayList<>();
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public String getKey() {
        return key;
    }
}
