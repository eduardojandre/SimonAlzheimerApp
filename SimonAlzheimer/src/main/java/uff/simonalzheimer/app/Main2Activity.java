package uff.simonalzheimer.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import uff.simonalzheimer.app.Fragments.AlertsFragment;
import uff.simonalzheimer.app.Fragments.DashboardFragment;
import uff.simonalzheimer.app.Fragments.RoutinesFragment;

public class Main2Activity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private AlertsFragment alertsFragment;
    private RoutinesFragment routinesFragment;
    private DashboardFragment dashFragment;
    private ServerConnectionStub serverConnect;

    private String patientName;
    private String patientNumber;
    private ArrayList<Routine> routines;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.main_layout, dashFragment).commit();
                    return true;
                case R.id.navigation_routines:
                    fragmentManager.beginTransaction().replace(R.id.main_layout, routinesFragment).commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentManager.beginTransaction().replace(R.id.main_layout, alertsFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        dashFragment = new DashboardFragment();
        routinesFragment = new RoutinesFragment();
        alertsFragment = new AlertsFragment();

        if(savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.main_layout, dashFragment).commit();
            navigation.setSelectedItemId(0);
        }

        serverConnect = ServerConnectionStub.getInstance();

        patientName = serverConnect.getPatientName();
        patientNumber = serverConnect.getPatientNumber();
        routines = serverConnect.getRoutines();
    }
    public void changeView(int id){
        switch (id) {
            case 0:
                navigation.setSelectedItemId(R.id.navigation_home);
                break;
            case 1:
                navigation.setSelectedItemId(R.id.navigation_routines);
                break;
            case 2:
                navigation.setSelectedItemId(R.id.navigation_notifications);
                break;
        }

    }

    public ArrayList<Routine> getRoutines(){
        routines = serverConnect.getRoutines();
        return routines;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public int getAlertsNumber(){
        return alertsFragment.getAlerts(this).size();
    }

    public ServerConnectionStub getServerConnect() {
        return serverConnect;
    }

    public int getRoutinesNumber() {
        return routines.size();
    }
}
