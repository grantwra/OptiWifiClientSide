package com.example.optiwificlient;

/**
 * Created by Vighnesh on 8/11/2016.
 */
public class ScanresultReplica {
    String Bssid;
    String Capability;
    int TimeStamp;

    public ScanresultReplica(){
        Bssid  = "qwerty";
        Capability = "asdasd";
        TimeStamp = 123;
    }// end constructor
    public ScanresultReplica(String b, String c, int t){
        Bssid = b;
        Capability = c;
        TimeStamp = t;
    }// end constructor overlading
    public ScanresultReplica( int t){
        Bssid = "zxczxc";
        Capability = "vbnvnb";
        TimeStamp = t;
    }// end constructor overloading


    public ScanresultReplica( String b,int t){
        Bssid = b;
        Capability = "vbnvnb";
        TimeStamp = t;
    }// end constructor overloading
}// end class
