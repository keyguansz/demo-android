package dji.dshare.s.act;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.io.IOException;

import act.dshare.dji.R;
import k.httpd.s.HelloServer;
import k.httpd.s.KFileScanner;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KFileScanner.test();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
