package com.example.optiwificlient;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;


/*public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}*/

public class MainActivity extends Activity implements View.OnClickListener
{
    static WifiManager wifi;
    ListView lv;
    TextView textStatus;
   // TextView tv;
    Button buttonScan;
    static int size = 0;
    static List<ScanResult> results;

    static String ITEM_KEY = "key";
    static ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    static SimpleAdapter adapter;

    static Context context2;

    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        String FILENAME = "newScanData";
        File newFile = new File(FILENAME);

        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            String IMEI = Settings.Secure.ANDROID_ID;

            fileOutputStream.write(IMEI.getBytes());

            fileOutputStream.close();

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        context2 = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus = (TextView) findViewById(R.id.textStatus);

	buttonScan = (Button) findViewById(R.id.buttonScan);
        buttonScan.setOnClickListener(this);
        lv = (ListView)findViewById(R.id.list);
      //  tv = (TextView)findViewById(R.id.textView);




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
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void onClick(View view)
    {
       // arraylist.clear();
        wifi.startScan();

        Toast.makeText(this, "Scanning...." + size, Toast.LENGTH_SHORT).show();
        try
        {
            size = size - 1;
            while (size >= 0)
            {
                HashMap<String, String> item = new HashMap<String, String>();
                item.put(ITEM_KEY, results.get(size).SSID + "  " + results.get(size).capabilities);

                arraylist.add(item);
                size--;
                adapter.notifyDataSetChanged();
            }
        }
        catch (Exception e)
        { }

    }


}

