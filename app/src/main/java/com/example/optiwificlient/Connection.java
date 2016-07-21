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
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import static android.net.wifi.WifiManager.calculateSignalLevel;


public class Connection extends BroadcastReceiver {

    HashMap<String, Long> map;

    //@Override


    public void onReceive(Context context, Intent intent) {


        map = MainActivity.map;



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

         //watch begins

        time_map((ArrayList<ScanResult>) scanResultList, scanResultList.size());

        clear_map_temp(scanResultList, scanResultList.size());

        //ArrayList<ScanResult> final_list = my_final_list((ArrayList<ScanResult>) scanResultList, map);

        ArrayList<ScanResult> final_list = compare_New_Scans(scanResultList, map);

        try {
            fileOutputStream = context.openFileOutput("newScanData", Context.MODE_APPEND);

            int size = final_list.size();

            String IMEI = map.size() + "\n";

            fileOutputStream.write(IMEI.getBytes());

            fileOutputStream.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // watch ends

       // for(int i = 0; i < scanResultList.size(); i++) {
         for(int i = 0; i < final_list.size(); i++){
            ScanResult result = final_list.get(i);
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


    }


    //POPULATES THE MAP
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void time_map(ArrayList<ScanResult> results, int size) {

        for ( int i = 0; i < size; i++) {

            String BBSid = results.get(i).BSSID;

            if (map.containsKey(BBSid) == false) {
               // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    map.put(results.get(i).BSSID, results.get(i).timestamp);
                //}
            } else {
                if (map.get(BBSid) == results.get(i).timestamp) {
                    map.remove(BBSid);
                   // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                    //}
                } else {
                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                    //}
                }

            }// end else
        }// end for
    }// end method

    public void clear_map_temp (List<ScanResult> results, int size){
        //Collection<Long> temp = map.values();
        //  Iterator it = map.entrySet().iterator();
        //List<String> local_list_ids = new List<String>();
        HashMap<String,Long> local_list_ids2 = new HashMap<String, Long>();

        for (String key : map.keySet()) {
            for( int i = 0; i < results.size(); i++) {
                if (key == results.get(i).BSSID) {
                    //local_list_ids.add(key);
                    local_list_ids2.put(key, results.get(i).timestamp);
                    // Concurrent modification
                    //map.remove(key);
                }
            }// end nested for
        }// end for each
       /* for(int j = 0; j < local_list_ids.size(); j++){
            String id = local_list_ids.get(j);
            map.remove(id);
        }// end for*/

        for(String Key: map.keySet()){
          /*  int breaker = 0;
            Iterator<String> iter = local_list_ids.iterator();
            while (iter.hasNext()){
               String str = iter.next();

                if(str == Key){
                    breaker = 1;
                    break;
                }
            }
            if(breaker == 0){
                map.remove(Key);
            }*/
            /*if(!local_list_ids2.containsKey(Key)){
                map.remove(Key);
            }*/


        }




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


    public ArrayList<ScanResult> compare_New_Scans(List<ScanResult> results, HashMap<String, Long> mapp){
        ArrayList<ScanResult> output = new ArrayList<ScanResult>();

        for(int i = 0; i < results.size(); i++){
            String BSSID = results.get(i).BSSID;
            if(map.containsKey(BSSID)){
                Long timestamp = results.get(i).timestamp;
                if(map.get(BSSID).equals(timestamp)){
                    output.add(results.get(i));
                }
            }
        }


        return output;
    }


}// end class
