package k.demo.gradle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import k.core.util.KLogUtil;
import k.core.util.TestLib;
import k.core.util.TestUtils;
import k.demo.gradle.R;


public class DemoActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);
        KLogUtil.D("apptag="+TestUtils.getTag()+",libtag = "+ TestLib.getTag());
        findViewById(R.id.tx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DemoActivity.this,DemoYst1Activity.class);
                startActivity(intent);
            }
        });

    }
}
