package com.example.optiwificlient;


import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static android.net.wifi.WifiManager.calculateSignalLevel;


public class Connection extends BroadcastReceiver {

    HashMap<String, Long> map;

    //@Override


    public void onReceive(Context context, Intent intent) {


        map = MainActivity.map;

        FileOutputStream fileOutputStream;

        File doesFileExist = new File("scans.json");
        List<ScanResult> scanResultList;
        scanResultList = MainActivity.wifi.getScanResults();

        time_map((ArrayList<ScanResult>) scanResultList, scanResultList.size());

        clear_map_temp(scanResultList, scanResultList.size());

        ArrayList<ScanResult> final_list = compare_New_Scans(scanResultList);

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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
         }
    }

    //POPULATES THE MAP
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void time_map(ArrayList<ScanResult> results, int size) {
        for ( int i = 0; i < size; i++) {
            String BBSid = results.get(i).BSSID;
            if (!map.containsKey(BBSid)) {
                    map.put(results.get(i).BSSID, results.get(i).timestamp);
            } else {
                if (map.get(BBSid) == results.get(i).timestamp) {
                    map.remove(BBSid);
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                } else {
                        map.put(results.get(i).BSSID, results.get(i).timestamp);
                }

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void clear_map_temp (List<ScanResult> results, int size){
        HashMap<String,Long> local_list_ids2 = new HashMap<>();
        ArrayList<String> toBeRemoved = new ArrayList<>();

        for (String key : map.keySet()) {
            for( int i = 0; i < size; i++) {
                if (Objects.equals(key, results.get(i).BSSID)) {
                    local_list_ids2.put(key, results.get(i).timestamp);
                }
            }
        }
        for(String Key2 : map.keySet()){
            if (!local_list_ids2.containsKey(Key2)){
                toBeRemoved.add(Key2);
            }
        }
        for(int x = 0; x < toBeRemoved.size(); x++){
            String temp = toBeRemoved.get(x);
            map.remove(temp);
        }
    }

    public ArrayList<ScanResult> compare_New_Scans(List<ScanResult> results){
        ArrayList<ScanResult> output = new ArrayList<>();
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
    }// end  method
}// end class
