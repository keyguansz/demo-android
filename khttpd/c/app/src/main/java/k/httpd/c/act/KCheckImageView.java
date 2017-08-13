package k.httpd.c.act;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import k.core.util.KTextUtil;
import k.httpd.c.act.dshare.dji.R;

public class KCheckImageView extends RelativeLayout {
	private float mStateAlpha = 0.3f;
	private boolean mOnlyDisable = false;
	private boolean mChecked;
	private final View mCheckView;
	private final TextView mSizeView;

	public ImageView getBgView() {
		return mBgView;
	}

	private final ImageView mBgView;

	public boolean isChecked() {
		mChecked = checked;
		return mChecked;
	}

	public void setChecked(boolean checked) {
		if (mChecked == checked){
			return;
		}
		mCheckView.setVisibility(checked ? VISIBLE : INVISIBLE);
		setAlpha(checked ? mStateAlpha : 1.0f );
		this.checked = checked;
	}
	public void setSize(long size) {
		mSizeView.setText(KTextUtil.formatASize(size));
	}
	public void setSize(long size, String str) {
		mSizeView.setText(KTextUtil.formatASize(size)+":"+str);
	}

	private boolean checked = false;
	public KCheckImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		CommonUtils.addView(this, R.layout.k_check_image_view);
		mCheckView = findViewById(R.id.k_check_iv);
		mBgView = (ImageView) findViewById(R.id.k_bg_iv);
		mSizeView = (TextView) findViewById(R.id.k_check_tv);
	}
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		boolean flg = (!mOnlyDisable && (isPressed() || isFocused())) || !isEnabled();
		if (flg) {
			setAlpha(mStateAlpha);
		} else {
			setAlpha(1.0f);
		}
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
