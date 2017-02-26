package key.android.demo.FitDemo.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import key.android.demo.FitDemo.R;

public class ConfigChangesActivity extends Activity {
   private static final String TAG = "ConfigChangesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 横屏
        setContentView(R.layout.activity_config_changes);
        /**
         *@desc
         *@ref:http://blog.csdn.net/chaoyu168/article/details/51005765
         *@author : key.guan @ 2017/2/26 21:59
         */

        Log.i(TAG,"onCreate");
    }

    /**
     *@desc      1.android:configChanges="keyboardHidden|orientation"
     * 才能监听
     * 2.y用户屏幕开关打开才行
     * 3.首次打开app，不执行onConfigurationChanged，有newConfig，先执行onConfigurationChanged，再执行onCreate
     * 4.port状态打开手机。 onCreate
     *                      -》onConfigurationChanged land=》onCreate；
     *                      -》onConfigurationChanged port
     *                      -》onConfigurationChanged land=》onCreate；
     *@author : key.guan @ 2017/2/26 21:21
     * http://www.jianshu.com/p/15d36c2f296b
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"onConfigurationChanged");
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)//land
        {
            Log.i(TAG, "onConfigurationChanged land");
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)//
        {
               Log.i(TAG, "onConfigurationChanged port");
        }
    }
}
