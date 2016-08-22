package com.example.optiwificlient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Vighnesh on 8/11/2016.
 */
public class ConnectReplica {
    HashMap<String, Integer> map;



    ArrayList<ScanresultReplica> list;

    public ConnectReplica(){
        list = new ArrayList<ScanresultReplica>();
        map = new HashMap<String, Integer>();
    }// end constructor

    public void populating_list(){
        ScanresultReplica a = new ScanresultReplica("ERN",1);
        ScanresultReplica b = new ScanresultReplica("Lali",2);
        ScanresultReplica c = new ScanresultReplica("Lekha",3);
        ScanresultReplica d = new ScanresultReplica("Misty",4);
        ScanresultReplica e = new ScanresultReplica("Vighnesh",5);
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
    }// end method

    public void time_map(ArrayList<ScanresultReplica> results, int size) {
        for ( int i = 0; i < size; i++) {
            String BBSid = results.get(i).Bssid;
            if (!map.containsKey(BBSid)) {
                map.put(results.get(i).Bssid, results.get(i).TimeStamp);
            } else {
                if (map.get(BBSid) == results.get(i).TimeStamp) {
                    map.remove(BBSid);
                    map.put(results.get(i).Bssid, results.get(i).TimeStamp);
                } else {
                    map.put(results.get(i).Bssid, results.get(i).TimeStamp);
                }

            }
        }
    }// end time_map


    public void clear_map_temp (ArrayList<ScanresultReplica> results, int size){
        HashMap<String,Integer> local_list_ids2 = new HashMap<>();
        ArrayList<String> toBeRemoved = new ArrayList<>();

        for (String key : map.keySet()) {
            for( int i = 0; i < size; i++) {
                if (Objects.equals(key, results.get(i).Bssid)) {
                    local_list_ids2.put(key, results.get(i).TimeStamp);
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
    }// end methd

    public ArrayList<ScanresultReplica> compare_New_Scans(ArrayList<ScanresultReplica> results){
        ArrayList<ScanresultReplica> output = new ArrayList<>();
        for(int i = 0; i < results.size(); i++){
            String BSSID = results.get(i).Bssid;
            if(map.containsKey(BSSID)){
                int timestamp = results.get(i).TimeStamp;
                if(map.get(BSSID).equals(timestamp)){
                    output.add(results.get(i));
                }
            }
        }
        return output;
    }// end  method
}// end class

