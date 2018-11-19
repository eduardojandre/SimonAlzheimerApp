package uff.simonalzheimer.app.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.Condition;
import uff.simonalzheimer.app.FileManager;
import uff.simonalzheimer.app.Main2Activity;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Routine;
import uff.simonalzheimer.app.ServerConnectionStub;
import uff.simonalzheimer.messages.Alert;

/**
 * Created by Juan Lucas Vieira on 11/11/2017.
 */

public class RoutineListAdapter extends ArrayAdapter<Routine> {

    private final Activity context;
    private ArrayList<Routine> routines;
    private int layout;
    private View rowView;

    private ServerConnectionStub serverConnection;

    private Main2Activity mainActivity;

    private boolean[] checked_positions;

    private boolean delete_mode = false;

    public RoutineListAdapter(Activity context, ArrayList<Routine> routines, int layout, Main2Activity mainActivity) {
        super(context, layout, routines);
        this.routines = routines;
        this.context = context;
        this.layout = layout;
        this.mainActivity = mainActivity;
        serverConnection = ServerConnectionStub.getInstance();
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(layout, null, true);
        TextView routine_title = (TextView) rowView.findViewById(R.id.routine_title);
        TextView conditions = (TextView) rowView.findViewById(R.id.cond_txt);
        TextView actions = (TextView) rowView.findViewById(R.id.act_txt);
        final Routine r = routines.get(position);

        //String.format(context.getString(R.string.bus_num_txt),a.getBus().getUniqueCode()
        routine_title.setText(String.format(context.getString(R.string.routine_title), position + 1));
        conditions.setText(condsToString(r.getConditions()));
        actions.setText(actsToString(r.getActions()));

        Switch onOffSwitch = (Switch) rowView.findViewById(R.id.routine_switch);
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
            sb.append(action);
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
            s.append("- "+c.getKey()+": "+c.getValue());
            if(!(conditions.indexOf(c) == conditions.size() - 1)){
                s.append("\n");
            }
        }
        Log.d("COND2String", s.toString());
        return s.toString();
    }

//    public void enableDeleteMode(){
//        checked_positions = new boolean[alerts.size()];
//        for (int i = 0; i < checked_positions.length; i++) {
//            checked_positions[i] = false;
//        }
//        delete_mode = true;
//        notifyDataSetChanged();
//    }

    public boolean isDeleteModeEnabled(){
        return delete_mode;
    }

//    public void deleteChecked(){
//        delete_mode = false;
//        ArrayList<Alert> aux = new ArrayList<>();
//        aux.addAll(alerts);
//        for (int i = 0; i < alerts.size(); i++) {
//            if(checked_positions[i]){
//                aux.remove(alerts.get(i));
//            }
//        }
//        alerts.clear();
//        alerts.addAll(aux);
//        FileManager.saveAlertsToFile(getContext(), alerts);
//        notifyDataSetChanged();
//    }
}
