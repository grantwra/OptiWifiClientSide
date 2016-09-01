package com.optiwifi.clientlibrary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.*;

public class ScanReciever extends BroadcastReceiver {

    static WifiManager myWifi;
    static HashMap<ScanResult, Long> myMap;
    Utils utils;

    public ScanReciever(WifiManager wifi, HashMap<ScanResult, Long> map) {
        myWifi = wifi;
        myMap = map;
        utils = new Utils(myMap);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        FileOutputStream fileOutputStream;
        File scanInforFile = new File("scans.json");
        List<ScanResult> scanResultList;
        scanResultList = myWifi.getScanResults();

        //For definitions on how these 3 methods work together please see the Documentation file

        utils.timeMapFill((ArrayList<ScanResult>) scanResultList, scanResultList.size());

        utils.clearMapOfOldScans(scanResultList, scanResultList.size());

        ArrayList<ScanResult> finalList = utils.compareNewScans(scanResultList);

        for(int i = 0; i < finalList.size(); i++){
            ScanResult result = finalList.get(i);
            Gson gson = new Gson();
            String temp = gson.toJson(result);
            try {

                fileOutputStream = context.openFileOutput(scanInforFile.getName(), Context.MODE_APPEND);
                String temp2 = temp + "\n";
                fileOutputStream.write(temp2.getBytes());
                fileOutputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
