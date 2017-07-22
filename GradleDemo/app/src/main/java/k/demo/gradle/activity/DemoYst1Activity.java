package k.demo.gradle.activity;

import android.app.Activity;
import android.os.Bundle;

import k.core.util.KLogUtil;
import k.core.util.TestLib;
import k.core.util.TestUtils;
import k.demo.gradle.R;


public class DemoYst1Activity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo_yst1);
        KLogUtil.D("apptag="+TestUtils.getTag()+",libtag = "+ TestLib.getTag());

    }
}
