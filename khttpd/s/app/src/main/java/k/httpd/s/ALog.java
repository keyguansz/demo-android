package k.httpd.s;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * @author :key.guan
 * @package :k.httpd.s
 * @date : 2017/6/19
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public abstract class ALog {
    private final String TAG = getClass().getSimpleName();
    private void LOG_E(String log){
        Log.e(TAG, log);
    }
    private void LOG_D(String log){
        Log.d(TAG, log);
    }
}
