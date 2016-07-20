package com.example.optiwificlient;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.JsonWriter;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import static android.net.wifi.WifiManager.calculateSignalLevel;


public class Connection extends BroadcastReceiver {

    HashMap<String, Long> map;

    //@Override


    public void onReceive(Context context, Intent intent) {

/*


        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled() == false)
        {
            Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
            wifi.setWifiEnabled(true);
        }
        this.adapter = new SimpleAdapter(MainActivity.this, arraylist, R.layout.activity_main, new String[] { ITEM_KEY }, new int[] { R.id.textStatus });
        lv.setAdapter(this.adapter);

        registerReceiver(new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context c, Intent intent)
            {
                results = wifi.getScanResults();
                size = results.size();
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));*/
/*
        MainActivity.wifi.startScan();

       // Toast.makeText(this, "Scanning...." + MainActivity.size, Toast.LENGTH_SHORT).show();
        try
        {
            MainActivity.size = MainActivity.size - 1;
            while (MainActivity.size >= 0)
            {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put(MainActivity.ITEM_KEY, MainActivity.results.get(MainActivity.size).SSID + "  " + MainActivity.results.get(MainActivity.size).capabilities);

                MainActivity.arraylist.add(item);
                MainActivity.size--;
                MainActivity.adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e)
        { }

*/

        //Toast.makeText(MainActivity.context2, "IM IN CONNECTION",Toast.LENGTH_LONG).show();

       /* MainActivity mainActivity = new MainActivity();
        View newView = null;
        mainActivity.onClick(newView);
        */

     /*   FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput("newScanData", Context.MODE_PRIVATE);

        } catch (){

        }
    */

    // **** this is just a testing try catch block
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = context.openFileOutput("newScanData", Context.MODE_APPEND);

            String IMEI = "this is a test!\n";

            fileOutputStream.write(IMEI.getBytes());

            fileOutputStream.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JsonWriter jsonWriter = null;

        File doesFileExist = new File("scans.json");
        List<ScanResult> scanResultList;
        scanResultList = MainActivity.wifi.getScanResults();

        /*// watch begins

        time_map((ArrayList<ScanResult>) scanResultList, scanResultList.size());

        clear_map_temp((ArrayList<ScanResult>) scanResultList, scanResultList.size());

        ArrayList<ScanResult> final_list = my_final_list((ArrayList<ScanResult>) scanResultList, map);

        // watch ends*/

        for(int i = 0; i < scanResultList.size(); i++) {
            ScanResult result = scanResultList.get(i);
            String BSSID = result.BSSID;
            String capabilities = result.capabilities;
            ///int centerFreq0 = result.centerFreq0;
            //int centerFreq1 = result.centerFreq1;
            //int channelWidth = result.channelWidth;
            int level = result.level;
            //CharSequence operatorFriendlyName = result.operatorFriendlyName;
            String SSID = result.SSID;
            //CharSequence venueName = result.venueName;
            int frequency = result.frequency;
            long timestamp = result.timestamp;
            int signalStrength = calculateSignalLevel(level, 100);


            try {
                JSONObject obj = new JSONObject();
                obj.put("timestamp", timestamp);
                obj.put("frequency", frequency);
                obj.put("capabilities", capabilities);
                obj.put("level", level);
                obj.put("Signal Strength", signalStrength);
                obj.put("SSID", SSID);
                JSONArray array = new JSONArray();
                array.put(obj);
                JSONObject finall = new JSONObject();
                finall.put(BSSID, array);
                String finalString = finall.toString();
                finalString = finalString + "\n";
                fileOutputStream = context.openFileOutput(doesFileExist.getName(), Context.MODE_APPEND);

                fileOutputStream.write(finalString.getBytes());

                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }// end for


        /****
        try { ***/
            //FileOutputStream out = new FileOutputStream(doesFileExist.getName());
            //jsonWriter = new JsonWriter(new OutputStreamWriter(out,"UTF-8"));
            //jsonWriter.setIndent("  ");
            /******
            jsonWriter.beginObject();
            jsonWriter.name("test");
            jsonWriter.beginArray();
            jsonWriter.beginObject();
            jsonWriter.name("hello").value("from the other side");
            jsonWriter.endObject();
            jsonWriter.endArray();
            jsonWriter.endObject();


            JSONObject obj = new JSONObject();
            obj.put("test","name");
            obj.put("newtest", "hello");
            JSONArray array = new JSONArray();
            array.put(obj);
            JSONObject finall = new JSONObject();
            finall.put("BSID", array);
            String finalString = finall.toString();
            finalString = finalString + "\n";
            fileOutputStream = context.openFileOutput(doesFileExist.getName(), Context.MODE_APPEND);

            fileOutputStream.write(finalString.getBytes());

            fileOutputStream.close();


            //jsonWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } ****/ /**finally {
            try {
                jsonWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }****/


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


}// end class
