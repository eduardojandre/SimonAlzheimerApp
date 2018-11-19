package uff.simonalzheimer.app.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Main2Activity;
import uff.simonalzheimer.messages.Alert;
import uff.simonalzheimer.app.FileManager;

/**
 * Created by Juan Lucas Vieira on 11/11/2017.
 */

public class AlertListAdapter extends ArrayAdapter<Alert> {

    private final Activity context;
    private ArrayList<Alert> alerts;
    private int layout;
    private View rowView;

    private Main2Activity mainActivity;

    private boolean[] checked_positions;

    private boolean delete_mode = false;

    public AlertListAdapter(Activity context, ArrayList<Alert> alerts, int layout, Main2Activity mainActivity) {
        super(context, layout, alerts);
        this.alerts = alerts;
        this.context = context;
        this.layout = layout;
        this.mainActivity = mainActivity;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(layout, null, true);
        TextView timeStamp = (TextView) rowView.findViewById(R.id.alert_timestamp);
        TextView msg = (TextView) rowView.findViewById(R.id.alert_msg);
        final Alert a = alerts.get(position);
        timeStamp.setText(a.getTimeStamp());
        msg.setText(a.getMessage());

        CheckBox checkBox = (CheckBox) rowView.findViewById(R.id.alert_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked_positions[position] = isChecked;
            }
        });

        if(delete_mode){
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        return rowView;
    }

    public ArrayList<Alert> getAllAlerts(){
        return alerts;
    }

    public void enableDeleteMode(){
        checked_positions = new boolean[alerts.size()];
        for (int i = 0; i < checked_positions.length; i++) {
            checked_positions[i] = false;
        }
        delete_mode = true;
        notifyDataSetChanged();
    }

    public boolean isDeleteModeEnabled(){
        return delete_mode;
    }

    public void deleteChecked(){
        delete_mode = false;
        ArrayList<Alert> aux = new ArrayList<>();
        aux.addAll(alerts);
        for (int i = 0; i < alerts.size(); i++) {
            if(checked_positions[i]){
                aux.remove(alerts.get(i));
            }
        }
        alerts.clear();
        alerts.addAll(aux);
        FileManager.saveAlertsToFile(getContext(), alerts);
        notifyDataSetChanged();
    }
}
