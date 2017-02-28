package key.android.demo.FitDemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import key.android.demo.FitDemo.R;

public class TmpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmp);
        TextView mTvSubVersion = (TextView)findViewById(R.id.tx);

        mTvSubVersion.setText(Html.fromHtml("<font color=#CACA00>STANDARD</font>"));
        mTvSubVersion.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));


        TextView mTvSubVersion2 = (TextView)findViewById(R.id.tx2);
        mTvSubVersion2.setText(Html.fromHtml("<sup><small>+</small></sup>"));
        mTvSubVersion2.setTextColor(getResources().getColor(android.R.color.holo_blue_bright));
    }
}
