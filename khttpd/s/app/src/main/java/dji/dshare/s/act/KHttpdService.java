package dji.dshare.s.act;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.logging.Logger;



public class KHttpdService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        LOG_E("KHttpdService--onBind()--");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
     
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LOG_E("KHttpdService--onUnbind()--");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        LOG_R("KHttpdService--onDestroy()--");
        super.onDestroy();
    }
    private final String TAG = getClass().getSimpleName();
    private void LOG_R(String log){
        Log.e(TAG, log);
    }
    private void LOG_E(String log){
        Log.e(TAG, log);
    }
    private void LOG_D(String log){
        Log.d(TAG, log);
    }
}
