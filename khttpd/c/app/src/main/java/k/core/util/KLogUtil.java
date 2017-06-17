package k.core.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


public class KLogUtil {
    private static final String TAG = "ART";

    public static void D(String log) {
        Log.e(TAG, log);
    }
    public static void E(String log) {
        Log.e(TAG, log);
    }

    public static void D(String clsName, String log) {
        D(clsName + "->" + log);
    }

    public static void D(String clsName, String methodName, String log) {
        D(clsName + "->" + methodName + "():" + log);
    }
    public static void E(String clsName, String methodName, String log) {
        E(clsName + "->" + methodName + "():" + log);
    }
    public static void E(String clsName, String log) {
        E(clsName + "->" + log);
    }
    public static void T(final String log) {
        Handler handler = new Handler(Looper.getMainLooper());//fix:Can't create handler inside thread that has not called Looper.prepare()
        handler.post(new Runnable() {
            @Override
            public void run() {
              //  Toast.makeText(KApplication.getIns(),log,Toast.LENGTH_LONG).show();
            }
        });
        Log.e(TAG, log);
    }
}
