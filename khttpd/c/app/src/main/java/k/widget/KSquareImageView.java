package k.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author :key.guan
 * @package :k.widget
 * @date : 2017/6/15
 * Description:
 * Copyright (k.httpd.c) 2017. DJI All Rights Reserved.
 */

public class KSquareImageView extends ImageView {

    public KSquareImageView(Context context) {
        super(context);
    }

    public KSquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KSquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
