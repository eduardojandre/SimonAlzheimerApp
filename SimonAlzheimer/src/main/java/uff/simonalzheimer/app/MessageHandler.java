package uff.simonalzheimer.app;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import uff.simonalzheimer.messages.Alert;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MessageHandler extends Handler {
	private static final int alertNotificationId = 1;
	private Context context;
	
	public MessageHandler(Context context)
	{
		this.context = context;
	}

	private void notifyUser(Alert alert){
		Toast.makeText(context, alert.getMessage(), Toast.LENGTH_LONG).show();

		alert.setTimeStamp(getTimeStamp());

		FileManager.saveAlert(context, alert);

		ServerConnectionStub stub=ServerConnectionStub.getInstance();
		ArrayList<Alert> alerts=stub.getAlerts();
		if(!alerts.contains(alert)){
			alerts.add(alert);
		}


		Intent intent = new Intent(this.context, uff.simonalzheimer.app.Activities.Main2Activity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this.context, 0, intent, 0);

		Notification n  = new Notification.Builder(this.context)
				.setContentTitle("Simon Alzheimer - Alert")
				.setContentText(alert.getMessage())
                .setSmallIcon(R.drawable.ic_warning_black_24dp)
				.setContentIntent(pIntent).build();


		NotificationManager notificationManager =
				(NotificationManager) this.context.getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify(alertNotificationId, n);
	}
	@Override
	public void handleMessage(Message msg) 
	{
		super.handleMessage(msg);
		
		if (msg.getData().getString("status") != null) 
		{
			String status = msg.getData().getString("status");
			
			if (status.equals("connected")) 
				Log.d("SDDL", (String) context.getResources().getText(R.string.msg_d_connected));
			else if (status.equals("disconnected")) 
				Log.d("SDDL", (String) context.getResources().getText(R.string.msg_d_disconnected));
			else if (status.equals("package")) 
			{
				Serializable s = msg.getData().getSerializable("package");
				
				if(s instanceof Alert)
				{
					notifyUser((Alert) s);
				}
				/* Here you can add different treatments to different types of 
				 * received data if you decide not to do that on the 
				 * NodeConnectionListener */
			}
			else
				Log.d("SDDL", status);
		}
	}

	private String getTimeStamp(){
		Date today = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
		String dateToStr = format.format(today);
		return dateToStr;
	}
}
