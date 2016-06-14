package com.example.optiwificlient;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonWriter;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String IMEI2 = null;
        String FILENAME = "Scan_Data";
        FileOutputStream fileOutputStream = null;

        File newFile = new File(FILENAME);

        try {
            fileOutputStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);

            String IMEI = Settings.Secure.ANDROID_ID;

            fileOutputStream.write(IMEI.getBytes());

            fileOutputStream.close();

            IMEI2 = IMEI;

        } catch (FileNotFoundException e) {
        e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView tv = new TextView(this);
        tv.setText(IMEI2);
        setContentView(tv);


    }
}
