package dji.dshare.s.act;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.IOException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import act.dshare.dji.BuildConfig;
import k.httpd.s.HelloServer;

import static android.R.attr.x;


public class KApplication extends Application {
    private static final String TAG = "KApplication";
    private HelloServer server2;
    private static Context mAppContext;
    public KApplication() {
        mAppContext = this;
    }

    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    //    x.Ext.init(this);
    //    x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

    /*    // 信任所有https域名
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });*/
        //startService(new Intent(this, KHttpdService.class));
        server2 = new HelloServer();
        try {
            // 启动web服务
            server2.start();
            Log.i(TAG, "The server started.");
        } catch(IOException ioe) {
            Log.w(TAG, "The server could not start."+ioe);
        }
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (server2 != null){
            server2.stop();
        }
        Log.w(TAG, "The server stopped.");
    }

}

