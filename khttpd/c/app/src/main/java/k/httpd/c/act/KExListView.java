package k.httpd.c.act;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ExpandableListView;

/**
 * @author :key.guan
 * @package :k.httpd.c.act
 * @date : 2017/6/25
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public class KExListView extends ExpandableListView {
    public KExListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean onGroupClick(ExpandableListView parent, View v,
                                int groupPosition, long id) {
        // TODO Auto-generated method stub
        return true;
    }
}
