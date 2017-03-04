package key.android.demo.uidemo.activity;

/**
 * @filename		: DJIMarqueeTextView.java
 * @package			: dji.pilot2.widget
 * @date			: 2015年7月12日 下午12:40:36
 * @author			: furong.liang
 *
 * Copyright (c) 2015, DJI All Rights Reserved.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class GTextView extends TextView {

    private boolean isPad = false;

    public GTextView(Context context)
    {
        this(context, null);
    }
    /**
     *@desc   Android系统中TextView实现跑马灯效果，必须具备以下几个条件：
    1、android:ellipsize=”marquee”
    2、TextView必须单行显示，即内容必须超出TextView大小
    3、TextView要获得焦点才能滚动（如果还不行，就要用自定义的TextView控件中重写isFocused()返回true就行
     *@ref:
     *@author : key.guan @ 2017/2/28 11:29
     */

    public GTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);


    }

    public GTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);

    }





    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();
    /**
     *@desc
     *@ref:http://www.cnblogs.com/huwei0814/p/5382039.html
     *@author : key.guan @ 2017/2/28 11:25
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[] {  0xFF429321, 0xFFB4EC51 },
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 +   mTextBound.height()/2, mPaint);
    }

    @Override
    public boolean isFocused()
    {
        return true;
    }

}

