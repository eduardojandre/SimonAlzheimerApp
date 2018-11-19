package uff.simonalzheimer.app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.Adapters.AlertListAdapter;
import uff.simonalzheimer.app.Adapters.RoutineListAdapter;
import uff.simonalzheimer.app.AddRoutineActivity;
import uff.simonalzheimer.app.FileManager;
import uff.simonalzheimer.app.Main2Activity;
import uff.simonalzheimer.app.MainActivity;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Routine;
import uff.simonalzheimer.app.SplashActivity;
import uff.simonalzheimer.messages.Alert;

/**
 * Created by Juan Lucas Vieira on 06/09/2017.
 */

public class RoutinesFragment extends Fragment {

    private FloatingActionButton add_fab;
    private ListView routineList;
    private TextView help_txt;
    private ImageView routine_icon;
    private ArrayList<Routine> routines = new ArrayList<>();
    private RoutineListAdapter adapter;

    private Main2Activity navActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routines, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navActivity = (Main2Activity) getActivity();
    }

    private void setListVisible() {
        help_txt.setVisibility(View.INVISIBLE);
        routine_icon.setVisibility(View.INVISIBLE);
        routineList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navActivity = (Main2Activity) getActivity();

        routineList = (ListView) getActivity().findViewById(R.id.routines_list_view);
        adapter = new RoutineListAdapter(getActivity(), routines, R.layout.routine_item, navActivity);

        add_fab = (FloatingActionButton) view.findViewById(R.id.add_btn);
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create_routine = new Intent(navActivity, AddRoutineActivity.class);
                startActivity(create_routine);
            }
        });

        routineList.setAdapter(adapter);

        help_txt = (TextView) getActivity().findViewById(R.id.routineTxtView);
        routine_icon = (ImageView) getActivity().findViewById(R.id.routines_icon);

    }

    private void displayEmpty(){
        help_txt.setVisibility(View.VISIBLE);
        routine_icon.setVisibility(View.VISIBLE);
        routineList.setVisibility(View.INVISIBLE);
        //delete_fab.hide();
    }

    @Override
    public void onResume() {
        super.onResume();

        ArrayList<Routine> routines_aux = navActivity.getRoutines();

        if(routines_aux != null && routines_aux.size() > 0){
            this.routines.clear();
            this.routines.addAll(routines_aux);
            adapter.notifyDataSetInvalidated();
            setListVisible();
            //delete_fab.show();
        } else {
            displayEmpty();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
