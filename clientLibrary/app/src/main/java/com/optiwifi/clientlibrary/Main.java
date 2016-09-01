package com.optiwifi.clientlibrary;

import android.content.Context;

public class Main {

    public void start(Context context){
        ServiceStart serviceStart = new ServiceStart();
        serviceStart.startServ(context);
    }
}
