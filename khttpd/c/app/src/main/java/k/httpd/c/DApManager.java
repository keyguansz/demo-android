package k.httpd.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static k.httpd.c.act.DpadShareSubItemAdapter.TAG;

/**
 * @author :key.guan
 * @package :k.httpd.c
 * @date : 2017/6/23
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public class DApManager {
    public enum ConState {
        ok, lose
    }

    private WifiManager mWifiManager;
    private static final DApManager ourInstance = new DApManager();

    public static DApManager getIns() {
        return ourInstance;
    }

    private DApManager() {
        mIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    //// TODO: 2017/6/23 getApplication?如何优化性能？，不能用
    public void init(Context ctx) {
        ctx.registerReceiver(mReceiver, mIntentFilter);
        mWifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
    }

    private final IntentFilter mIntentFilter;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
            String str = "";
            if (networkInfo != null) {
                str = str + "networkInfo:" + networkInfo;
                if (networkInfo.isConnected()) {
                    ping("192.168.1.3", 0);
                }
            }
            if (wifiInfo != null) {
                str = str + "************wifiInfo" + wifiInfo + ",mac=" + wifiInfo.getMacAddress() + ",ip=" +
                        formatIpAddress(wifiInfo.getIpAddress());
            }


            LOG("str=" + str);
            //  wifiNetInfo.
        }
    };

    public void post() {

    }

    //guset:10.80.54.229
    //cryststal:192.168.1.125
    //phone:192.168.43.125
    public static boolean isWifiViaMobile(int ip) {
        String ipStr = formatIpAddress(ip);
        String ipMin = "192.168.42.2";
        String ipMax = "192.168.48.254";
        boolean isFlg = ipMin.compareTo(ipStr) <= 0 && ipMax.compareTo(ipStr) >= 0;
        //  DJILogUtil.I("checkIp:ipStr=" + ipStr + " is in [" + ipMin + "," + ipMax + "] = " + isFlg );
        return isFlg;
    }

    private static String formatIpAddress(int ipAdress) {
        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    public static int ping(String ip, boolean isLevel) {
        // 手机与遥控器的连接速度
        int TotalCount = 3;
        long TotalTime = 0;
        int PingCnt = 0;
        int AvgTime = 300;
        int signalLevel = 0;
        for (int i = 0; i < TotalCount; i++) {
            int status = -1;
            long LastTime = System.currentTimeMillis();
            try {
                if (InetAddress.getByName(ip).isReachable(AvgTime)) {
                    status = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (status == 0) {
                long DiffTime = System.currentTimeMillis() - LastTime;
                TotalTime += DiffTime;
                PingCnt++;
            } else {
                PingCnt = 0;
                break;
            }
        }

        if (PingCnt == TotalCount) {
            AvgTime = (int) TotalTime / TotalCount;

            if (AvgTime < 100) {
                AvgTime = 0;
                signalLevel = 4;
            } else if (AvgTime < 250) {
                signalLevel = 3;
            } else if (AvgTime < 500) {
                signalLevel = 2;
            } else if (AvgTime < 1000) {
                signalLevel = 1;
            }
        }
        if (isLevel) {
            return signalLevel;
        } else {
            int t = (1000 - AvgTime) / 10;
            return t < 0 ? 0 : t;
        }
    }

    public static boolean isReachable(String host, int timeout) throws UnknownHostException, IOException {
        return InetAddress.getByName(host).isReachable(timeout);
    }

    private void LOG(String str) {
        Log.e(TAG, str);
    }
}
