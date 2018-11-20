package uff.simonalzheimer.app;

import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import uff.simonalzheimer.messages.Registration;

/**
 * MainActivity: This is our application's MainActivity. It consists in 
 * 				 a UUID randomly generated and shown in txt_uuid, a text 
 * 				 field for the IP:PORT in et_ip, a "Ping!" button 
 * 				 (btn_ping) to send a Ping object message, a "Start 
 * 				 Service!" button (btn_startservice) to start the 
 * 				 communication service and a "Stop Service!" button 
 * 				 (btn_stopservice) to stop it.
 * 
 * @author andremd
 * 
 */
public class MainActivity extends Activity {

	/* Shared Preferences */
	private static String uniqueID = null;
	private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* GUI Elements */
		Button btn_register = findViewById(R.id.btn_register);

        StartService();
		/* Ping Button Listener*/
        btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isMyServiceRunning(CommunicationService.class))
					Toast.makeText(getBaseContext(), getResources().getText(R.string.msg_e_servicenotrunning), Toast.LENGTH_SHORT).show();
				else
				{
					Registration registerRequest = new Registration();
					registerRequest.setType(Registration.ClientType.Caregiver);

					/* Calling the SendPingMsg action to the PingBroadcastReceiver */
					Intent i = new Intent(MainActivity.this, CommunicationService.class);
					i.setAction("lac.contextnet.sddl_pingservicetest.broadcastmessage." + "ActionSendPingMsg");
					i.putExtra("lac.contextnet.sddl_pingservicetest.broadcastmessage." + "ExtraPingMsg", registerRequest);
					LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(i);
				}
			}
		});
	}
	
	//See http://stackoverflow.com/questions/600207/how-to-check-if-a-service-is-running-in-android
	private boolean isMyServiceRunning(Class<?> serviceClass) {
	    ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
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
        Intent intent = new Intent(MainActivity.this, CommunicationService.class);
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
	            Editor editor = sharedPrefs.edit();
	            editor.putString(PREF_UNIQUE_ID, uniqueID);
	            editor.commit();
	        }
	    }
	    return uniqueID;
	}
}
