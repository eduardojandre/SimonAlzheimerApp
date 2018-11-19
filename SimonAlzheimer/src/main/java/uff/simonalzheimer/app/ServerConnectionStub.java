package uff.simonalzheimer.app;

import java.util.ArrayList;

/**
 * Created by Juan Lucas Vieira on 19/11/2018.
 */

public class ServerConnectionStub {

    public final Condition c1 = new Condition("HEART_BEAT","HIGH");

    private String patientName = "Alzira";
    private String patientNumber = "988887777";

    ArrayList<Routine> routines = new ArrayList<>();

    private static ServerConnectionStub singletonInstance;

    private ServerConnectionStub(){}

    public static ServerConnectionStub getInstance(){
        if (singletonInstance == null){ //if there is no instance available... create new one
            singletonInstance = new ServerConnectionStub();
        }

        return singletonInstance;
    }

    public String getPatientName(){
        return patientName;
    }
    public String getPatientNumber(){
        return patientNumber;
    }

    public ArrayList<Routine> getRoutines(){
        if(routines.size() == 0) {
            Routine r1 = new Routine();
            r1.addCondition(RuleValues.ACTIVITY, RuleValues.LYING);
            r1.addCondition(RuleValues.LOCATION, RuleValues.KITCHEN);
            r1.addAction(RuleValues.CALL_EMERGENCY);
            routines.add(r1);

            Routine r2 = new Routine();
            r2.addCondition(RuleValues.TEMPERATURE, RuleValues.HIGH);
            r2.addAction(RuleValues.ALERT_CAREGIVER);
            routines.add(r2);

            Routine r3 = new Routine();
            r3.addCondition(RuleValues.TEMPERATURE, RuleValues.HIGH);
            r3.addCondition(RuleValues.ACTIVITY, RuleValues.LYING);
            r3.addCondition(RuleValues.LOCATION, RuleValues.KITCHEN);
            r3.addAction(RuleValues.ALERT_CAREGIVER);
            routines.add(r3);

            Routine r4 = new Routine();
            r4.addCondition(RuleValues.TEMPERATURE, RuleValues.HIGH);
            r4.addCondition(RuleValues.ACTIVITY, RuleValues.RUNNING);
            r4.addCondition(RuleValues.LOCATION, RuleValues.OUTDOOR);
            r4.addCondition(RuleValues.BLOOD_PRESSURE, RuleValues.LOW);
            r4.addAction(RuleValues.ALERT_BOTH);
            routines.add(r4);
        }
        return routines;
    }

    public ArrayList<PossibleCondition> getPossibleConds(){
        ArrayList<PossibleCondition> pos = new ArrayList<>();
        pos.add(RuleValues.possibleConditionFactory(RuleValues.HEART_BEAT));
        pos.add(RuleValues.possibleConditionFactory(RuleValues.ACTIVITY));
        pos.add(RuleValues.possibleConditionFactory(RuleValues.BLOOD_PRESSURE));
        pos.add(RuleValues.possibleConditionFactory(RuleValues.LOCATION));
        pos.add(RuleValues.possibleConditionFactory(RuleValues.TEMPERATURE));
        return pos;
    }

    public ArrayList<String> getActions(){
        ArrayList<String> actions = new ArrayList<>();
        actions.add(RuleValues.CALL_EMERGENCY);
        actions.add(RuleValues.ALERT_PATIENT);
        actions.add(RuleValues.ALERT_BOTH);
        actions.add(RuleValues.ALERT_CAREGIVER);
        actions.add(RuleValues.ALERT_FAMILIAR);
        return actions;
    }

    public void addRoutine(Routine newRoutine) {
        routines.add(newRoutine);
    }

    public void setRoutineEnabled(Routine r, boolean enabled){
        routines.get(routines.indexOf(r)).setEnabled(enabled);
    }
}
