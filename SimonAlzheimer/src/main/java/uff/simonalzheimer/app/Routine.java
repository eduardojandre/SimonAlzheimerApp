package uff.simonalzheimer.app;

import java.util.ArrayList;


public class Routine {

    ArrayList<Condition<String,String>> conditions;
    ArrayList<String> actions;
    boolean enabled = true;

    public Routine() {
        this.conditions = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public void addCondition(String key, String value, boolean notValue){
        conditions.add(new Condition<>(key, value, notValue));
    }

    public void addAction(String action){
        actions.add(action);
    }

    public ArrayList<Condition<String, String>> getConditions() {
        return conditions;
    }

    public ArrayList<String> getActions() {
        return actions;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
