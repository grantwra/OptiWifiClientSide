package com.optiwifi.clientlibrary;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import java.util.HashMap;


public class ServiceStart extends Service {

    static WifiManager wifi;
    static HashMap<ScanResult, Long> map;

    static Context tempMain;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        // Code to execute when the service is first created
        wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        map = new HashMap<>();
    }

    @Override
    public void onDestroy() {
        //want to run forever
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        ScanReciever broadcastReciever = new ScanReciever(wifi, map);
        registerReceiver(broadcastReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        startForeground(1,new Notification());

        return START_STICKY;
    }

    public void startServ(Context main){
        tempMain = main;
        Intent intent = new Intent(tempMain, ServiceStart.class);
        tempMain.startService(intent);
    }
}
