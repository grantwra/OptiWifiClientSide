package com.example.optiwificlient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.io.File;


/*public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

public class MainActivity extends Activity {
    static WifiManager wifi;
    static int size = 0;
    static List<ScanResult> results;

    static String ITEM_KEY = "key";
    static ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    static SimpleAdapter adapter;

    static Context context2;


    static HashMap<String, Long> map;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
     private GoogleApiClient client;


    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        map = new HashMap<String, Long>();

        //setContentView(R.layout.activity_main);

        // Im adding this comment as a test

        String FILENAME = "newScanData";
        File newFile = new File(FILENAME);

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            String IMEI = Settings.Secure.ANDROID_ID + "\n";

            fileOutputStream.write(IMEI.getBytes());

            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Connection broadcastReciever = new Connection();
        registerReceiver(broadcastReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        //registerReceiver(broadcastReciever, new IntentFilter(new Action("android.net.wifi.SCAN_RESULTS")));

        /*for( String name : map.keySet()){
            String value = map.get(name).toString();
            System.out.println(name + "  " + value);
        }*/




    }// end onCreate



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.optiwificlient/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.optiwificlient/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void time_map(ArrayList<ScanResult> results, int size) {

        for ( int i = 0; i < size; i++) {

            String BBSid = results.get(i).BSSID;

            if (map.containsKey(BBSid) == false) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    map.put(results.get(i).BSSID, results.get(i).timestamp);
                }
            } else {
                if (map.get(BBSid) == results.get(i).timestamp) {
                    map.remove(BBSid);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                    }
                }

            }// end else
        }// end for
    }// end method

    public void clear_map_temp ( ArrayList<ScanResult> results, int size){
        //Collection<Long> temp = map.values();
      //  Iterator it = map.entrySet().iterator();
        ArrayList<String> local_list_ids = new ArrayList<String>();

        for (String key : map.keySet()) {
            for( int i = 0; i < results.size(); i++) {
                if (key == results.get(i).BSSID) {
                    local_list_ids.add(key);
                    // Concurrent modification
                    //map.remove(key);
                }
            }// end nested for
        }// end for each
        for(int j = 0; j < local_list_ids.size(); j++){
            String id = local_list_ids.get(j);
            map.remove(id);
        }// end for


    }// end method

    public ArrayList<ScanResult> my_final_list (ArrayList<ScanResult> results, HashMap<String, Long> map)
    {
        ArrayList<ScanResult> output = new ArrayList<ScanResult>();

        for (String key : map.keySet()) {
            for( int i = 0; i < results.size(); i++) {
                if (key == results.get(i).BSSID) {
                   // local_list_ids.add(key);
                    output.add(results.get(i));
                    // Concurrent modification
                    //map.remove(key);
                }
            }// end nested for
        }// end for each

        return output;
    }// end method

}





