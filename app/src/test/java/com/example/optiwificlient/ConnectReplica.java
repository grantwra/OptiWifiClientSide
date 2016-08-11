package com.example.optiwificlient;

import java.util.ArrayList;
import java.util.HashMap;

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
}// end class

