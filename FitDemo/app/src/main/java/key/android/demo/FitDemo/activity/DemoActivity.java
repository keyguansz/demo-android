package key.android.demo.FitDemo.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import key.android.demo.FitDemo.R;


public class DemoActivity extends BaseActivity {
    private String TAG = "DemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        StringBuilder data = new StringBuilder();
        getScreenDensity_ByWindowManager();
        getScreenDensity_ByResources();
        getDefaultScreenDensity();

        /**
         *@desc
         *@ref: android-View工作原理（三）视图大小计算过程（measure过程）
        http://xixinfei.iteye.com/blog/2114701

         *@author : key.guan @ 2017/2/25 14:02
         */
    /*   View v = findViewById(R.id.data);
        LinearLayout.LayoutParams p  = (LinearLayout.LayoutParams) findViewById(R.id.data).getLayoutParams();
        p.weight = 1;*/

    }
    /**
     *@desc   Android中屏幕分辨率可以有三种方式进行获取，一种是通过WindowManager获取，第二种是通过Resources获取，第三种是Display获取。 可结合具体环境灵活使用。
    其中获取的屏幕宽高单位为px，屏幕密度的获取可以参考两个值density和densityDpi,可根据需要自行获取。
     *@ref: http://blog.csdn.net/netwalk/article/details/10115729
     *@author : key.guan @ 2017/2/24 22:57
     */

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
