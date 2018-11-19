package uff.simonalzheimer.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.Adapters.AlertListAdapter;
import uff.simonalzheimer.app.FileManager;
import uff.simonalzheimer.messages.Alert;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Main2Activity;

/**
 * Created by Juan Lucas Vieira on 06/09/2017.
 */

public class AlertsFragment extends Fragment {

    private FloatingActionButton delete_fab;
    private ListView alertsList;
    private TextView help_txt;
    private ImageView bell_icon;
    private ArrayList<Alert> alerts = new ArrayList<>();
    private AlertListAdapter adapter;

    private Main2Activity navActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alerts, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navActivity = (Main2Activity) getActivity();
    }

    private void setListVisible() {
        help_txt.setVisibility(View.INVISIBLE);
        bell_icon.setVisibility(View.INVISIBLE);
        alertsList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navActivity = (Main2Activity) getActivity();

        alertsList = (ListView) getActivity().findViewById(R.id.alerts_list_view);
        adapter = new AlertListAdapter(getActivity(), alerts, R.layout.alert_item, navActivity);

        delete_fab = (FloatingActionButton) view.findViewById(R.id.delete_btn);
        delete_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.isDeleteModeEnabled()){
                    adapter.deleteChecked();
                    delete_fab.setImageResource(R.drawable.ic_delete_white_24dp);
                    if(adapter.isEmpty()){
                        displayEmpty();
                    }
                } else {
                    delete_fab.setImageResource(R.drawable.ic_check_white_24dp);
                    adapter.enableDeleteMode();
                }
            }
        });

        alertsList.setAdapter(adapter);

        help_txt = (TextView) getActivity().findViewById(R.id.alertsTxtView);
        bell_icon = (ImageView) getActivity().findViewById(R.id.bell_icon);

    }

    private void displayEmpty(){
        help_txt.setVisibility(View.VISIBLE);
        bell_icon.setVisibility(View.VISIBLE);
        alertsList.setVisibility(View.INVISIBLE);
        delete_fab.hide();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Alert> read_alerts = getAlerts(getContext());

        if(read_alerts != null && read_alerts.size() > 0){
            this.alerts.clear();
            this.alerts.addAll(read_alerts);
            adapter.notifyDataSetInvalidated();
            setListVisible();
            delete_fab.show();
        } else {
            displayEmpty();
        }
    }

    public ArrayList<Alert> getAlerts(Context c){
        ArrayList<Alert> read_alerts = FileManager.readAlerts(c);

        //Debug
        //TODO:Remove this later.
        read_alerts = new ArrayList<>();
        read_alerts.add(new Alert("18/11/2018 16:43:20", "Alzira forgot to take the medicine!"));
        read_alerts.add(new Alert("18/11/2018 16:45:20", "Alzira heartrate is high!"));

        return read_alerts;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
