package k.httpd.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * @author :key.guan
 * @package :k.httpd.c
 * @date : 2017/6/23
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

class DApManager {
    private  WifiManager mWifiManager;
    private static final DApManager ourInstance = new DApManager();

    static DApManager getInstance() {
        return ourInstance;
    }

    private DApManager() {
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
     

    }
    //// TODO: 2017/6/23 getApplication?如何优化性能？，不能用
    private void init(Context ctx){
        ctx.registerReceiver(mReceiver, mIntentFilter);
        mWifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }
    private final IntentFilter mIntentFilter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConnected = wifiNetInfo!=null && wifiNetInfo.isConnected();


        }
    };
}
