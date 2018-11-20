package uff.simonalzheimer.app.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.Fragments.ActionPopUp;
import uff.simonalzheimer.app.Fragments.ConditionPopUp;
import uff.simonalzheimer.app.PossibleCondition;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Routine;
import uff.simonalzheimer.app.ServerConnectionStub;

public class AddRoutineActivity extends AppCompatActivity implements ConditionPopUp.ConditionPopUpListener, ActionPopUp.ActionPopUpListener {

    Routine newRoutine;
    ServerConnectionStub serverConnection;
    ArrayList<PossibleCondition> possibilities;
    ArrayList<String> keys;
    ArrayList<String> actions;
    Bundle condBundle;
    Bundle valuesBundle;

    ImageButton addConditionBtn;
    ImageButton addActionBtn;
    FloatingActionButton done_fab;

    TextView conditionTxt;
    TextView actionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_routine);

        serverConnection = ServerConnectionStub.getInstance();
        possibilities = serverConnection.getPossibleConds();

        newRoutine = new Routine();
        keys = new ArrayList<>();
        actions = new ArrayList<>();

        addConditionBtn = findViewById(R.id.addCondition);
        addActionBtn = findViewById(R.id.addActionBtn);
        done_fab = findViewById(R.id.done_fab);
        done_fab.setVisibility(View.GONE);

        conditionTxt = findViewById(R.id.conditionPlaceholder);
        conditionTxt.setText("");
        actionTxt = findViewById(R.id.actionPlaceholder);
        actionTxt.setText("");

        conditionTxt.setVisibility(View.GONE);
        actionTxt.setVisibility(View.GONE);

        for (PossibleCondition p: possibilities) {
            keys.add(p.getKey());
        }

        actions = serverConnection.getActions();

        done_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serverConnection.addRoutine(newRoutine);
                finish();
            }
        });

        addConditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConditionPopup();
            }
        });

        addActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActionPopup();
            }
        });
    }

    private void openConditionPopup(){
        FragmentManager fm = getSupportFragmentManager();
        ConditionPopUp conditionPopUp = new ConditionPopUp();
        conditionPopUp.setCancelable(false);

        condBundle = new Bundle();
        valuesBundle = new Bundle();

        for (PossibleCondition p: possibilities) {
            valuesBundle.putStringArrayList(p.getKey(), p.getValues());
        }

        condBundle.putStringArrayList("keys",keys);
        condBundle.putBundle("values",valuesBundle);

        conditionPopUp.setArguments(condBundle);
        conditionPopUp.show(fm, "fragment_condition_popup");
    }

    @Override
    public void onFinishCondition(String condition, String value, boolean notValue) {
        Log.d("Popup Reply", condition + ": " + value);
        newRoutine.addCondition(condition, value, notValue);
        keys.remove(condition);
        if(keys.size() == 0){
            addConditionBtn.setVisibility(View.GONE);
        }
        conditionTxt.setVisibility(View.VISIBLE);
        if(notValue){
            value = "Not "+value;
        }
        if(conditionTxt.getText().length()> 0) {
            conditionTxt.setText(conditionTxt.getText().toString() + "\n- " + condition + ": " + value);
        } else {
            conditionTxt.setText("- "+condition + ": " + value);
        }
        verifyValidRoutine();
    }

    private void verifyValidRoutine() {
        if(newRoutine.getActions().size() > 0 && newRoutine.getConditions().size() > 0){
            done_fab.setVisibility(View.VISIBLE);
        }
    }

    private void openActionPopup(){
        FragmentManager fm = getSupportFragmentManager();
        ActionPopUp actionPopUp = new ActionPopUp();
        actionPopUp.setCancelable(false);

        Bundle b = new Bundle();
        b.putStringArrayList("actions",actions);

        actionPopUp.setArguments(b);
        actionPopUp.show(fm, "fragment_actions_popup");
    }

    @Override
    public void onFinishAction(String action) {
        Log.d("Popup Reply", action);
        newRoutine.addAction(action);
        actions.remove(action);
        if(actions.size() == 0){
            addActionBtn.setVisibility(View.GONE);
        }
        actionTxt.setVisibility(View.VISIBLE);
        if(actionTxt.getText().length()> 0) {
            actionTxt.setText(actionTxt.getText().toString() + "\n- " + action);
        } else {
            actionTxt.setText("- "+ action);
        }
        verifyValidRoutine();
    }
}
