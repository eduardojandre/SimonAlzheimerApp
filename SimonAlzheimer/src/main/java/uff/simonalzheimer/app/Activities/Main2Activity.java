package uff.simonalzheimer.app.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import uff.simonalzheimer.app.CommunicationService;
import uff.simonalzheimer.app.Fragments.AlertsFragment;
import uff.simonalzheimer.app.Fragments.DashboardFragment;
import uff.simonalzheimer.app.Fragments.RoutinesFragment;
import uff.simonalzheimer.app.IPPort;
import uff.simonalzheimer.app.R;
import uff.simonalzheimer.messages.Routine;
import uff.simonalzheimer.app.ServerConnectionStub;
import uff.simonalzheimer.messages.Registration;

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

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public static Main2Activity _this;
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
        _this=this;
        StartService();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getSupportActionBar().setTitle(getString(R.string.app_name));

        navigation = findViewById(R.id.navigation);
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
    public void sendMsg(Serializable content){
        Intent i = new Intent(Main2Activity.this, CommunicationService.class);
        i.setAction("lac.contextnet.sddl_pingservicetest.broadcastmessage." + "ActionSendPingMsg");
        i.putExtra("lac.contextnet.sddl_pingservicetest.broadcastmessage." + "ExtraPingMsg", content);
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(i);
    }
    public void register(){
        if(isMyServiceRunning(CommunicationService.class)){
            Registration registerRequest = new Registration();
            registerRequest.setType(Registration.ClientType.Caregiver);

            sendMsg(registerRequest);
            sendMsg(getRoutines());
        }
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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void StopService(){
        stopService(new Intent(getBaseContext(), CommunicationService.class));
    }
    private void StartService(){
        String ipPort = getResources().getText(R.string.ipValue).toString();


        if(!IPPort.IPRegexChecker(ipPort))
        {
            Toast.makeText(getBaseContext(), getResources().getText(R.string.msg_e_invalid_ip), Toast.LENGTH_LONG).show();
            return;
        }

        IPPort ipPortObj = new IPPort(ipPort);

				/* Starting the communication service */
        Intent intent = new Intent(this, CommunicationService.class);
        intent.putExtra("ip", ipPortObj.getIP());
        intent.putExtra("port", Integer.valueOf(ipPortObj.getPort()));
        intent.putExtra("uuid", GetUUID(getBaseContext()));
        startService(intent);

    }
    //See http://androidsnippets.com/generate-random-uuid-and-store-it
    public synchronized static String GetUUID(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
}
