package k.httpd.c.act;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import k.art.R;


public class Ch12MainActivity extends Activity {
    private final String TAG = "Ch12MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ch12main);
        initView();
    }

    private void initView() {
        GridView gv = (GridView) findViewById(R.id.gv);
        gv.setAdapter(new GridAdapter(this));
    }
}
