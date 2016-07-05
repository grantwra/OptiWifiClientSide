package com.example.optiwificlient;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class Connection extends BroadcastReceiver {

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

    }

}
