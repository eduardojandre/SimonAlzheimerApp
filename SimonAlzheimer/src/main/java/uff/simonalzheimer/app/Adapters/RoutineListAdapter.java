package uff.simonalzheimer.app.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.messages.Condition;
import uff.simonalzheimer.app.Activities.Main2Activity;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.messages.Routine;
import uff.simonalzheimer.app.ServerConnectionStub;


public class RoutineListAdapter extends ArrayAdapter<Routine> {

    private final Activity context;
    private ArrayList<Routine> routines;
    private int layout;

    private ServerConnectionStub serverConnection;

    public RoutineListAdapter(Activity context, ArrayList<Routine> routines, int layout, Main2Activity mainActivity) {
        super(context, layout, routines);
        this.routines = routines;
        this.context = context;
        this.layout = layout;
        Main2Activity mainActivity1 = mainActivity;
        serverConnection = ServerConnectionStub.getInstance();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(layout, null, true);
        TextView routine_title = rowView.findViewById(R.id.routine_title);
        TextView conditions = rowView.findViewById(R.id.cond_txt);
        TextView actions = rowView.findViewById(R.id.act_txt);
        final Routine r = routines.get(position);

        routine_title.setText(r.getName());
        conditions.setText(condsToString(r.getConditions()));
        actions.setText(actsToString(r.getActions()));

        Switch onOffSwitch = rowView.findViewById(R.id.routine_switch);
        onOffSwitch.setChecked(r.isEnabled());

        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                serverConnection.setRoutineEnabled(r, isChecked);
            }
        });

        return rowView;
    }

    private String actsToString(ArrayList<String> actions) {
        StringBuilder sb = new StringBuilder();
        for (String action: actions) {
            sb.append("- ").append(action);
            if (!(actions.indexOf(actions) == actions.size() - 1)){
                sb.append("\n");
            }
        }
        Log.d("ACt2String", sb.toString());
        return sb.toString();
    }

    private String condsToString(ArrayList<Condition<String, String>> conditions) {
        StringBuilder s = new StringBuilder();
        for (Condition c: conditions) {
            if(c.getNotValueBool()){
                s.append("- ").append(c.getKey()).append(": Not ").append(c.getValue());
            } else {
                s.append("- ").append(c.getKey()).append(": ").append(c.getValue());
            }
            if(!(conditions.indexOf(c) == conditions.size() - 1)){
                s.append("\n");
            }
        }
        Log.d("COND2String", s.toString());
        return s.toString();
    }
}
