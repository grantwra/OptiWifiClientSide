package com.optiwifi.clientlibrary;

import android.annotation.TargetApi;
import android.net.wifi.ScanResult;
import android.os.Build;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    static HashMap<ScanResult, Long> myMap;
    public Utils(HashMap<ScanResult, Long> map){
        myMap = map;
    }

    //POPULATES THE MAP
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void timeMapFill(ArrayList<ScanResult> results, int size) {
        for ( int i = 0; i < size; i++) {
            ScanResult sr = results.get(i);
            if (!myMap.containsKey(sr)) {
                myMap.put(results.get(i), results.get(i).timestamp);
            } else {
                if (myMap.get(sr) == results.get(i).timestamp) {
                    myMap.remove(sr);
                    myMap.put(results.get(i), results.get(i).timestamp);
                } else {
                    myMap.put(results.get(i), results.get(i).timestamp);
                }

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void clearMapOfOldScans(List<ScanResult> results, int size){
        HashMap<ScanResult,Long> localListIds2 = new HashMap<>();
        ArrayList<ScanResult> toBeRemoved = new ArrayList<>();
        for (ScanResult key : myMap.keySet()) {
            for(int i = 0; i < size; i++) {
                if (key.equals(results.get(i))) {
                    localListIds2.put(key, results.get(i).timestamp);
                }
            }
        }
        for(ScanResult Key2 : myMap.keySet()){
            if (!localListIds2.containsKey(Key2)){
                toBeRemoved.add(Key2);
            }
        }
        for(int x = 0; x < toBeRemoved.size(); x++){
            ScanResult temp = toBeRemoved.get(x);
            myMap.remove(temp);
        }
    }

    public ArrayList<ScanResult> compareNewScans(List<ScanResult> results){
        ArrayList<ScanResult> output = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            ScanResult sr = results.get(i);
            if(myMap.containsKey(sr)){
                Long timestamp = results.get(i).timestamp;
                if(myMap.get(sr).equals(timestamp)){
                    output.add(results.get(i));
                }
            }
        }
        return output;
    }
}
