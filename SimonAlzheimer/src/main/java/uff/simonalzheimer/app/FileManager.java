package uff.simonalzheimer.app;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import uff.simonalzheimer.messages.Alert;



public class FileManager {

    public static void saveAlert(Context c, Alert a){
        try {
            FileOutputStream outStream = c.openFileOutput("account.sim", Context.MODE_APPEND);
            outStream.write((a.getTimeStamp() + "#" +a.getMessage()+ "\n").getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Alert> readAlerts(Context c) {
        try {
            FileInputStream inputConfigStream = c.openFileInput("alerts.sim" );
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputConfigStream));
            String line;
            ArrayList<Alert> alerts = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] info = line.split("#");
                alerts.add(new Alert(info[0],info[1]));
            }
            inputConfigStream.close();
            return alerts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveAlertsToFile(Context c, ArrayList<Alert> alerts) {
        File f;
        try {
            f = new File(c.getFilesDir(), "alerts.cfg");
            FileOutputStream out = new FileOutputStream(f, false);
            for (Alert a : alerts) {
                out.write((a.getTimeStamp() + "#" +a.getMessage()+ "\n").getBytes());
            }
            out.close();
            Log.d("ALERTS SAVED TO", f.getAbsolutePath());
            return true;
        } catch (Exception io){
            io.printStackTrace();
            return false;
        }
    }
}
