package k.httpd.c.act;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashSet;

import k.core.util.kil.KRawImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.ICsProtocolSet;

public class DJIDshareView extends LinearLayout {
    private final String TAG = "DJIDshareView";
    private ListView videoElv;
    private ListView photoElv;
    private KListAdapter videoAdapter;
    private KListAdapter imageAdapter;

    public static HashSet<String> mSelected = new HashSet<>();
    private TextView stateTv;
    private TextView selectTv;

    private interface Tab{
        int video = 0;
        int photo = 1;
    }
    public DJIDshareView(Context context) {
        this(context, null);
    }

    public DJIDshareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        CommonUtils.addView(this, R.layout.dshare_view);
        if (isInEditMode()) {
            return;
        }
        init();
    }

    private void init() {
        ((RadioGroup)findViewById(R.id.rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.photo_rb){
                    updateView(Tab.photo);
                }else {
                    updateView(Tab.video);
                }
            }
        });
        videoElv = (ListView) findViewById(R.id.video_elv);
        videoAdapter = new KListAdapter(getContext(), ICsProtocolSet.ExtType.video);
        videoAdapter.setHandler(mMainHandler);
        videoElv.setAdapter(videoAdapter);

       /* videoElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });*/

        photoElv = (ListView) findViewById(R.id.photo_elv);
        imageAdapter = new KListAdapter(getContext(), ICsProtocolSet.ExtType.image);
        imageAdapter.setHandler(mMainHandler);
        photoElv.setAdapter(imageAdapter);
       /* photoElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });*/
       findViewById(R.id.down_tv).setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               KRawImgLoader.getIns().start(mSelected, mMainHandler);
           }
       });
        findViewById(R.id.reset_tv).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                KRawImgLoader.getIns().reset();
            }
        });

        stateTv = (TextView) findViewById(R.id.state_tv);
        selectTv = (TextView) findViewById(R.id.select_tv);
        updateView(Tab.video);
    }
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
          //  Log.e(TAG, ",msg="+msg);//为何不能
            if (msg.what == KRawImgLoader.MSG.MSG_select){
                selectTv.setText("selected "+ mSelected.size()+" items");
                return;
            }
            String str = "what="+msg.what+":"+msg.arg2+"/"+msg.arg1;
            if (msg.obj != null){
                str = str + ":" + msg.obj + "%";
            }
            stateTv.setText(str);

            if (msg.what == KRawImgLoader.MSG.MSG_LOADING) {

            } else if (msg.what == KRawImgLoader.MSG.MSG_FINISH) {

            }
        }        ;
    };

    private void updateView(int tabId){
        if (tabId == Tab.photo){
            imageAdapter.updateView();
            photoElv.setVisibility(VISIBLE);
            videoElv.setVisibility(INVISIBLE);
          /*  for (int i = 0 ; i < imageAdapter.getGroupCount(); i++){
                photoElv.expandGroup(i);
            }*/
        }else {
            photoElv.setVisibility(INVISIBLE);
            videoAdapter.updateView();
            videoElv.setVisibility(VISIBLE);
           /* for (int i = 0 ; i < imageAdapter.getGroupCount(); i++){
                videoElv.expandGroup(i);
            }*/
        }
    }
}
