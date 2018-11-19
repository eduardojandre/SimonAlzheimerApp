package uff.simonalzheimer.app.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import uff.simonalzheimer.app.FileManager;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.app.Main2Activity;
import uff.simonalzheimer.app.ServerConnectionStub;
import uff.simonalzheimer.messages.Alert;

/**
 * Created by Juan Lucas Vieira on 06/09/2017.
 */

public class DashboardFragment extends Fragment {

    private Main2Activity navActivity;
    //TODO: Change this later to get the actual number.


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navActivity = (Main2Activity) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navActivity = (Main2Activity) getActivity();

        TextView patient_name = (TextView) view.findViewById(R.id.patient_name);
        LinearLayout alerts_layout = (LinearLayout) view.findViewById(R.id.alerts_dash_layout);
        LinearLayout routines_layout = (LinearLayout) view.findViewById(R.id.routines_dash_layout);
        ImageButton call_btn = (ImageButton) view.findViewById(R.id.call_btn);
        ImageButton msg_btn = (ImageButton) view.findViewById(R.id.msg_btn);

        //String.format(context.getString(R.string.bus_num_txt),a.getBus().getUniqueCode()
        TextView routines_msg_txt = (TextView) view.findViewById(R.id.routines_msg);
        TextView alert_msg_txt = (TextView) view.findViewById(R.id.alert_msg);

        patient_name.setText(navActivity.getPatientName());
        routines_msg_txt.setText(String.format(getContext().getString(R.string.dash_routine_msg),navActivity.getRoutinesNumber()));
        alert_msg_txt.setText(String.format(getContext().getString(R.string.dash_alert_msg),navActivity.getAlertsNumber()));

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callPatient = new Intent(Intent.ACTION_DIAL);
                callPatient.setData(Uri.parse("tel:" + navActivity.getPatientNumber()));
                startActivity(callPatient);
            }
        });

        msg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent msgPatient = new Intent(Intent.ACTION_SENDTO);
                msgPatient.setData(Uri.parse("smsto:" + navActivity.getPatientNumber()));
                startActivity(msgPatient);
            }
        });

        alerts_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Alerts","Clicked");
                navActivity.changeView(2);
            }
        });

        routines_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Routines","Clicked");
                navActivity.changeView(1);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
