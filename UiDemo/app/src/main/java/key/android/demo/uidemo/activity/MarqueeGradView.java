package key.android.demo.uidemo.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import key.android.demo.uidemo.R;

/**
 * @desc Android系统中TextView实现跑马灯效果，必须具备以下几个条件：
 * 1、android:ellipsize=”marquee”
 * 2、TextView必须单行显示，即内容必须超出TextView大小
 * 3、TextView要获得焦点才能滚动（如果还不行，就要用自定义的TextView控件中重写isFocused()返回true就行
 */
public class MarqueeGradView extends RelativeLayout {

    private boolean isPad = false;
    private TextView mTv;

    public MarqueeGradView(Context context) {
        this(context, null);
        init(context);
    }
    public MarqueeGradView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MarqueeGradView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_marquee_grad, null, false);
        addView(v);
        mTv = (TextView) v.findViewById(R.id.marquee_grad_tv);
        if (!isPad) {
            mTv.setFocusable(true);
            mTv.setFocusableInTouchMode(true);
            mTv.setSingleLine();
            mTv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            mTv.setMarqueeRepeatLimit(-1);
        }
    }
}

