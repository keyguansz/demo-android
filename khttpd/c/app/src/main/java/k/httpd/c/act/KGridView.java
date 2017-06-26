package k.httpd.c.act;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author :key.guan
 * @package :k.httpd.c.act
 * @date : 2017/6/26
 * Description:
 * Copyright (c) 2017. DJI All Rights Reserved.
 */

public class KGridView extends GridView {


    public KGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
