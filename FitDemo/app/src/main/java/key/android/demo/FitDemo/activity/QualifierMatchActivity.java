package key.android.demo.FitDemo.activity;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import key.android.demo.FitDemo.R;

/**
 *@desc  1.sw432 > w>h>Size(large)> >land >768*432(按照资源文件顺序来的！)
 *       2.w,h是通过getResources().getDisplayMetrics()来得到的smallest app 1536 x 1486；不是我们普通意义的长宽
 *        在默认条件下没有layout，才会考虑xhdpi->xxhdpi:在手机和平板再次验证吧
 *@ref:
 *@author : key.guan @ 2017/3/4 17:44
 */
public class QualifierMatchActivity extends Activity {
    String TAG = "QualifierMatchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualifier_match);
        getScreenDensity_ByWindowManager();
        getScreenDensity_ByResources();
        getDefaultScreenDensity();

        // getResources().getString()
    }
    //获得手机的宽度和高度像素单位为px
    // 通过WindowManager获取
    public void getScreenDensity_ByWindowManager() {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();//屏幕分辨率容器
        getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        Log.d(TAG, "getScreenDensity_ByWindowManager,Screen mDisplayMetrics: " + mDisplayMetrics);
    }

    // 通过Resources获取
    public void getScreenDensity_ByResources() {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        Log.d(TAG, "getScreenDensity_ByResources,Screen mDisplayMetrics: " + mDisplayMetrics);
    }

    // 获取屏幕的默认分辨率
    public void getDefaultScreenDensity() {
        Display mDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        mDisplay.getSize(size);
        Log.d(TAG, "getScreenDensity_ByResources,Screen mDisplay: " + mDisplay);
        Log.d(TAG, "Screen size: " + size.toString());
    }
}
