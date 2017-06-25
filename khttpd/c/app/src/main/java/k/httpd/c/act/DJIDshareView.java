package k.httpd.c.act;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashSet;

import k.core.util.kil.KRawImgLoader;
import k.httpd.c.act.dshare.dji.R;
import k.httpd.c.cons.ICsProtocolSet;
import k.httpd.c.model.FileInfoModel;


public class DJIDshareView extends LinearLayout {

    private ExpandableListView videoElv;
    private ExpandableListView photoElv;
    private KExListAdapter videoAdapter;
    private KExListAdapter imageAdapter;

    private HashSet<String> mSelected = new HashSet<>();

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
        videoElv = (ExpandableListView) findViewById(R.id.video_elv);
        videoAdapter = new KExListAdapter(getContext(), ICsProtocolSet.ExtType.video);
        videoElv.setAdapter(videoAdapter);

       /* videoElv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true;
            }
        });*/

        photoElv = (ExpandableListView) findViewById(R.id.photo_elv);
        imageAdapter = new KExListAdapter(getContext(), ICsProtocolSet.ExtType.image);
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
               KRawImgLoader.getIns().start(mSelected);
           }
       });
        updateView(Tab.video);
    }

    private void updateView(int tabId){
        if (tabId == Tab.photo){
            imageAdapter.updateView();
            photoElv.setVisibility(VISIBLE);
            videoElv.setVisibility(INVISIBLE);
            for (int i = 0 ; i < imageAdapter.getGroupCount(); i++){
                photoElv.expandGroup(i);
            }
        }else {
            photoElv.setVisibility(INVISIBLE);
            videoAdapter.updateView();
            videoElv.setVisibility(VISIBLE);
            for (int i = 0 ; i < imageAdapter.getGroupCount(); i++){
                videoElv.expandGroup(i);
            }
        }
    }
}
