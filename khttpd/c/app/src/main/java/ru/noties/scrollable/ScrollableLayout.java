package ru.noties.scrollable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import k.httpd.c.act.dshare.dji.R;


/**
 * <p>
 * This is the main {@link android.view.ViewGroup} for implementing Scrollable.
 * It has the same as {@link FrameLayout#onMeasure(int, int)} measure logic,
 * but has it's own {@link #onLayout(boolean, int, int, int, int)} logic.
 * </p>
 * <p>
 * Note, that this ViewGroup will layout it's children as if it were an ordinary {@link android.widget.LinearLayout}
 * with orientation set to {@link android.widget.LinearLayout#VERTICAL}.
 * No paddings or margins will affect the layout position of children,
 * <p>
 * although margins will certainly affect measurements.
 * </p>
 * <p>
 * The best usage would be to include two {@link View} views.
 * The first one would represent the <code>header</code> logic and the second would be scrollable container.
 * Note, that we should make use of {@link android.view.ViewGroup.LayoutParams#MATCH_PARENT}
 * as the height attribute for scrollable container. Because it directly affects the scrollable behavior.
 * If you wish to create a <code>sticky</code> effect for one of the views in <code>header</code> ViewGroup,
 * you could specify <code>layout_marginTop</code> attribute for your scrollable layout,
 * which represents the height of your sticky element.
 * </p>
 * <p>
 * The logic behind scenes is simple. We should be able to encapsulate scrollable logic in a way (in a <code>tabs</code> case),
 * that saves us from adding header placeholders and footers (so that scrollY does
 * not change when different tab is selected) to every {@link android.widget.ScrollView}, {@link android.widget.AbsListView} etc.
 * So, instead of modifying scrolling behavior of each scrollable View, we are creating our own
 * ViewGroup that handles it for us.
 * </p>
 * <p>
 * Follow these steps to create your own Scrollable layout:
 * </p>
 * <p>
 * <b>Simple case</b>
 * <pre>
 * {@code
 *     <ru.noties.scrollable.ScrollableLayout
 *          android:layout_width="match_parent"
 *          android:layout_height="match_parent"
 *          app:scrollable_maxScroll="@dimen/header_height"> <!-- (!) -->
 *
 *          <View
 *              android:layout_width="match_parent"
 *              android:layout_height="@dimen/header_height" /> <!-- (!) -- >
 *
 *          <ListView
 *              android:layout_width="match_parent"
 *              android:layout_height="match_parent" />
 *
 *     </ru.noties.scrollable.ScrollableLayout>
 * }
 * </pre>
 * <p>
 * <b>Sticky case</b>
 * (of cause it's just an xml step, you also should implement translation logic in OnScrollChangeListener
 * {@link #setOnScrollChangedListener(OnScrollChangedListener)})
 * <pre>
 *     {@code
 *     <ru.noties.scrollable.ScrollableLayout
 *          android:layout_width="match_parent"
 *          android:layout_height="match_parent"
 *          app:scrollable_maxScroll="@dimen/header_height">
 *
 *          <LinearLayout
 *              android:layout_width="match_parent"
 *              android:layout_height="wrap_content">
 *
 *              <View
 *                  android:layout_width="match_parent"
 *                  android:layout_height="@dimen/header_height" />
 *
 *              <View
 *                  android:layout_width="match_parent"
 *                  android:layout_height="@dimen/sticky_height" /> <!-- (!) -->
 *
 *          </LinearLayout>
 *
 *          <ListView
 *              android:layout_width="match_parent"
 *              android:layout_height="match_parent"
 *              android:layout_marginTop="@dimen/sticky_height" /> <!-- (!) -->
 *     }
 * </pre>
 * <p>
 * Created by Dimitry Ivanov (mail@dimitryivanov.ru) on 28.03.2015.
 *
 * Changed by Trey Cai on 2017-02-27
 */
// KWillRefactor: 2017/6/25 这么的类，好多系统是有的哦。。 ScrollableLayout没有必要吧，不就是想要一个滑动的控件么？
public class ScrollableLayout extends FrameLayout {

    private static final int DEFAULT_CONSIDER_IDLE_MILLIS = 100;

    private SmoothScroller mScroller;
    private GestureDetector mScrollDetector;
    private GestureDetector mFlingDetector;

    private CanScrollVerticallyDelegate mCanScrollVerticallyDelegate;
    private OnScrollChangedListener mOnScrollChangedListener;

    private int mMaxScrollY;

    private boolean mIsScrolling;
    private boolean mIsFlinging;

    private MotionEventHook mMotionEventHook;

    private View mDraggableView;
    private boolean mIsDraggingDraggable;
    private final Rect mDraggableRect;
    {
        mDraggableRect = new Rect();
    }

    private long mConsiderIdleMillis;

    private int mScrollableContinueY;
    private long mScrollableContinueYStartedMillis;
    private long mScrollableContinueYDuration;

    private OnFlingOverListener mOnFlingOverListener;

    private boolean mAutoMaxScroll;
    private ViewTreeObserver.OnGlobalLayoutListener mAutoMaxScrollYLayoutListener;
    private int mAutoMaxScrollViewId;

    public ScrollableLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {

        final TypedArray array = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollableLayout);
        try {

            //final boolean flyWheel = array.getBoolean(R.styleable.ScrollableLayout_scrollable_scrollerFlywheel, false);
            //mScroller = initScroller(context, new LinearInterpolator(), flyWheel);
            mScroller = new SmoothScroller(context, new LinearInterpolator());

            final float friction = array.getFloat(R.styleable.ScrollableLayout_scrollable_friction, Float.NaN);
            if (friction == friction) {
                setFriction(friction);
            }

            mMaxScrollY = array.getDimensionPixelSize(R.styleable.ScrollableLayout_scrollable_maxScroll, 0);
            mAutoMaxScroll = array.getBoolean(R.styleable.ScrollableLayout_scrollable_autoMaxScroll, mMaxScrollY == 0);
            mAutoMaxScrollViewId = array.getResourceId(R.styleable.ScrollableLayout_scrollable_autoMaxScrollViewId, 0);

            final long considerIdleMillis = array.getInteger(
                    R.styleable.ScrollableLayout_scrollable_considerIdleMillis,
                    DEFAULT_CONSIDER_IDLE_MILLIS
            );
            setConsiderIdleMillis(considerIdleMillis);

        } finally {
            array.recycle();
        }

        setVerticalScrollBarEnabled(true);

        mScrollDetector = new GestureDetector(context, new ScrollGestureListener());
        mFlingDetector = new GestureDetector(context, new FlingGestureListener(context));

        mMotionEventHook = new MotionEventHook(new MotionEventHookCallback() {
            @Override
            public void apply(MotionEvent event) {
                ScrollableLayout.super.dispatchTouchEvent(event);
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (mAutoMaxScroll) {
            processAutoMaxScroll(true);
        }
    }

    ///**
    // * Override this method if you wish to create own {@link android.widget.Scroller}
    // *
    // * @param context      {@link Context}
    // * @param interpolator {@link Interpolator}, the default implementation passes <code>null</code>
    // * @param flywheel     {@link android.widget.Scroller#Scroller(Context, Interpolator, boolean)}
    // * @return new instance of {@link android.widget.Scroller} must not bu null
    // */
    //protected ScrollableScroller initScroller(Context context, Interpolator interpolator, boolean flywheel) {
    //    return new ScrollableScroller(context, interpolator, flywheel);
    //}

    /**
     * Sets friction for current {@link android.widget.Scroller}
     *
     * @param friction to be applied
     * @see android.widget.Scroller#setFriction(float)
     */
    public void setFriction(float friction) {
        mScroller.setFriction(friction);
    }

    /**
     * @param delegate which will be invoked when scroll state of scrollable children is needed
     * @see CanScrollVerticallyDelegate
     */
    public void setCanScrollVerticallyDelegate(CanScrollVerticallyDelegate delegate) {
        this.mCanScrollVerticallyDelegate = delegate;
    }

    /**
     * Also can be set via xml attribute <code>scrollable_maxScroll</code>
     *
     * @param maxY the max scroll y available for this View.
     * @see #getMaxScrollY()
     */
    public void setMaxScrollY(int maxY) {
        this.mMaxScrollY = maxY;
    }

    /**
     * @return value which represents the max scroll distance to <code>this</code> View (aka <code>header</code> height)
     * @see #setMaxScrollY(int)
     */
    public int getMaxScrollY() {
        return mMaxScrollY;
    }

    /**
     * Note that this value might be set with xml definition (<pre>{@code app:scrollable_considerIdleMillis="100"}</pre>)
     *
     * @param millis millis after which current scroll
     *               state would be considered idle and thus firing close up logic if set
     * @see #getConsiderIdleMillis()
     * @see #DEFAULT_CONSIDER_IDLE_MILLIS
     */
    public void setConsiderIdleMillis(long millis) {
        mConsiderIdleMillis = millis;
    }

    /**
     * @return current value of millis after which scroll state would be considered idle
     * @see #setConsiderIdleMillis(long)
     */
    public long getConsiderIdleMillis() {
        return mConsiderIdleMillis;
    }

    /**
     * Pass an {@link OnScrollChangedListener}
     * if you wish to get notifications when scroll state of <code>this</code> View has changed.
     * It\'s helpful for implementing own logic which depends on scroll state (e.g. parallax, alpha, etc)
     *
     * @param listener to be invoked when {@link #onScrollChanged(int, int, int, int)} has been called.
     *                 Might be <code>null</code> if you don\'t want to receive scroll notifications anymore
     */
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.mOnScrollChangedListener = listener;
    }

    public void setOnFlingOverListener(OnFlingOverListener onFlingOverListener) {
        this.mOnFlingOverListener = onFlingOverListener;
    }

    /**
     * @see View#onScrollChanged(int, int, int, int)
     * @see OnScrollChangedListener#onScrollChanged(int, int, int)
     */
    @Override
    public void onScrollChanged(int l, int t, int oldL, int oldT) {

        final boolean changed = t != oldT;

        if (changed && mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(t, oldT, mMaxScrollY);
        }
    }

    /**
     * @see View#scrollTo(int, int)
     * @see #setCanScrollVerticallyDelegate(CanScrollVerticallyDelegate)
     * @see #setMaxScrollY(int)
     */
    @Override
    public void scrollTo(int x, int y) {

        final int newY = getNewY(y);

        if (newY < 0) {
            return;
        }

        super.scrollTo(0, newY);
    }

    public void setToBegin(){

        super.scrollTo(0,0);
    }

    /**
     * If set to true then ScrollableLayout will listen for global layout change of a view with
     * is passed through xml: scrollable_autoMaxScrollViewId OR first view in layout.
     * With this feature no need to specify `scrollable_maxScrollY` attribute
     *
     * @param autoMaxScroll to listen for child view height and change mMaxScrollY accordingly
     */
    public void setAutoMaxScroll(boolean autoMaxScroll) {
        mAutoMaxScroll = autoMaxScroll;
        processAutoMaxScroll(mAutoMaxScroll);
    }

    /**
     * @return `mAutoMaxScroll` value
     * @see #setAutoMaxScroll(boolean)
     */
    public boolean isAutoMaxScroll() {
        return mAutoMaxScroll;
    }

    protected void processAutoMaxScroll(boolean autoMaxScroll) {

        if (getChildCount() == 0) {
            return;
        }

        final View view;
        if (mAutoMaxScrollViewId != 0) {
            view = findViewById(mAutoMaxScrollViewId);
        } else {
            view = getChildAt(0);
        }

        if (view == null) {
            return;
        }

        if (!autoMaxScroll) {
            if (mAutoMaxScrollYLayoutListener != null) {
                ViewUtils.removeGlobalLayoutListener(view, mAutoMaxScrollYLayoutListener);
                mAutoMaxScrollYLayoutListener = null;
            }
        } else {
            // if it's not null, we have already set it
            if (mAutoMaxScrollYLayoutListener == null) {
                mAutoMaxScrollYLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        mMaxScrollY = view.getMeasuredHeight();
                    }
                };
                view.getViewTreeObserver().addOnGlobalLayoutListener(mAutoMaxScrollYLayoutListener);
            }
        }
    }

    protected int getNewY(int y) {

        final int currentY = getScrollY();

        if (currentY == y) {
            return -1;
        }

        final int direction = y - currentY;
        final boolean isScrollingBottomTop = direction < 0;

        if (mCanScrollVerticallyDelegate != null) {

            if (isScrollingBottomTop) {

                // if not dragging draggable then return, else do not return
                if (!mIsDraggingDraggable
                        && mCanScrollVerticallyDelegate.canScrollVertically(direction)) {
                    return -1;
                }
            } else {

                // else check if we are at max scroll
                if (currentY == mMaxScrollY
                        && !mCanScrollVerticallyDelegate.canScrollVertically(direction)) {
                    return -1;
                }
            }
        }

        if (y < 0) {
            y = 0;
        } else if (y > mMaxScrollY) {
            y = mMaxScrollY;
        }

        return y;
    }

    /**
     * Sets View which should be included in receiving scroll gestures.
     * Maybe be null
     *
     * @param view you wish to include in scrolling gestures (aka tabs)
     */
    public void setDraggableView(View view) {
        mDraggableView = view;
    }

    public boolean ismIsFlinging(){
        return mIsFlinging;
    }

    @Override
    public boolean dispatchTouchEvent(@SuppressWarnings("NullableProblems") MotionEvent event) {

        final int action = event.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {

            mScroller.abortAnimation();

            if (mDraggableView != null && mDraggableView.getGlobalVisibleRect(mDraggableRect)) {
                final int x = (int) (event.getRawX() + .5F);
                final int y = (int) (event.getRawY() + .5F);
                mIsDraggingDraggable = mDraggableRect.contains(x, y);
            } else {
                mIsDraggingDraggable = false;
            }
        }

        final boolean isPrevScrolling = mIsScrolling;
        final boolean isPrevFlinging = mIsFlinging;

        mIsFlinging = mFlingDetector.onTouchEvent(event);
        mIsScrolling = mScrollDetector.onTouchEvent(event);

        removeCallbacks(mScrollRunnable);
        if (mIsHeaderScrollingEnabled) {
            post(mScrollRunnable);
        }

        final boolean isIntercepted = mIsScrolling || mIsFlinging;
        final boolean isPrevIntercepted = isPrevScrolling || isPrevFlinging;

        final boolean shouldRedirectDownTouch = action == MotionEvent.ACTION_MOVE
                && (!isIntercepted && isPrevIntercepted)
                && getScrollY() == mMaxScrollY;

        if (isIntercepted || isPrevIntercepted) {

            mMotionEventHook.hook(event, MotionEvent.ACTION_CANCEL);

            if (!isPrevIntercepted) {
                return true;
            }
        }

        if (shouldRedirectDownTouch) {
            mMotionEventHook.hook(event, MotionEvent.ACTION_DOWN);
        }

        super.dispatchTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            final int oldY = getScrollY();
            final int nowY = mScroller.getCurrY();
            scrollTo(0, nowY);
            if (oldY != nowY) {
                onScrollChanged(0, getScrollY(), 0, oldY);
            }
            postInvalidate();
        }
    }

    @Override
    protected int computeVerticalScrollRange() {
        return mMaxScrollY;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childTop = 0;
        for (int i = 0; i < getChildCount(); i++) {
            final View view = getChildAt(i);
            view.layout(left, childTop, right, childTop + view.getMeasuredHeight());
            childTop += view.getMeasuredHeight();
        }
        //super.onLayout(changed, left, top, right, bottom);
    }

    private boolean mIsHeaderScrollingEnabled = true;

    public void disableHeaderScrolling() {
        mIsHeaderScrollingEnabled = false;
    }

    public void enableHeaderScrolling() {
        mIsHeaderScrollingEnabled = true;
    }

    private final Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {

            final boolean isContinue = mScroller.computeScrollOffset();
            //mSelfUpdateFling = isContinue;

            if (isContinue) {

                final int y = mScroller.getCurrY();
                final int nowY = getScrollY();
                final int diff = y - nowY;

                if (diff != 0) {
                    scrollBy(0, diff);
                } else {
                    if (mOnFlingOverListener != null) {
                        if (mScrollableContinueY > 0) {
                            final long duration
                                    = mScrollableContinueYDuration - (System.currentTimeMillis() - mScrollableContinueYStartedMillis);
                            mOnFlingOverListener.onFlingOver(mScrollableContinueY, duration);
                            mScrollableContinueY = 0;
                        }
                    }
                }

                post(this);
            }
        }
    };

    private class ScrollGestureListener extends GestureListenerAdapter {

        private final int mTouchSlop;

        {
            final ViewConfiguration vc = ViewConfiguration.get(getContext());
            mTouchSlop = vc.getScaledTouchSlop();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (!mIsHeaderScrollingEnabled) {
                return false;
            }

            final float absX = Math.abs(distanceX);

            if (absX > Math.abs(distanceY)
                    || absX > mTouchSlop) {
                return false;
            }

            final int now = getScrollY();
            scrollBy(0, (int) (distanceY + .5F));

            return now != getScrollY();
        }
    }

    private class FlingGestureListener extends GestureListenerAdapter {

        private static final int MIN_FLING_DISTANCE_DIP = 12;

        private final int mMinFlingDistance;
        private final float mMinVelocity;

        FlingGestureListener(Context context) {
            this.mMinFlingDistance = DipUtils.dipToPx(context, MIN_FLING_DISTANCE_DIP);

            final ViewConfiguration configuration = ViewConfiguration.get(context);
            this.mMinVelocity = configuration.getScaledMinimumFlingVelocity();
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (Math.abs(velocityY) < mMinVelocity) {
                return false;
            }

            if (Math.abs(velocityX) > Math.abs(velocityY)) {
                return false;
            }

            final int nowY = getScrollY();
            if (nowY < 0 || nowY > mMaxScrollY) {
                return false;
            }

            final int maxPossibleFinalY;
            final int duration;
            if (mOnFlingOverListener != null && mIsHeaderScrollingEnabled) {
                // we will pass Integer.MAX_VALUE to calculate the maximum possible fling
                mScroller.fling(0, nowY, 0, -(int) (velocityY + .5F), 0, 0, 0, Integer.MAX_VALUE);
                maxPossibleFinalY = mScroller.getFinalY();
                duration = mScroller.getSplineFlingDuration(velocityY);

                mScroller.abortAnimation();
            } else {
                maxPossibleFinalY = 0;
                duration = 0;
            }

            if (mIsHeaderScrollingEnabled) {
                mScroller.fling(0, nowY, 0, -(int) (velocityY + .5F), 0, 0, 0, mMaxScrollY);
            }

            if (mScroller.computeScrollOffset()) {

                final int suggestedY = mScroller.getFinalY();

                if (Math.abs(nowY - suggestedY) < mMinFlingDistance) {
                    mScroller.abortAnimation();
                    return false;
                }

                final int finalY;
                if (suggestedY == nowY) {
                    finalY = suggestedY;
                } else {
                    finalY = 0;
                }

                final int scrollableContinueY;
                if (maxPossibleFinalY > 0) {
                    if (maxPossibleFinalY > mMaxScrollY) {
                        scrollableContinueY = maxPossibleFinalY - mMaxScrollY;
                    } else {
                        scrollableContinueY = 0;
                    }
                } else {
                    scrollableContinueY = 0;
                }
                mScrollableContinueY = scrollableContinueY;
                if (mScrollableContinueY > 0) {
                    mScrollableContinueYStartedMillis = System.currentTimeMillis();
                    mScrollableContinueYDuration = duration;
                }

                final int newY = getNewY(finalY);

                return !(finalY == nowY || newY < 0);
            } else {
                mScrollableContinueY = 0;
            }

            return false;
        }
    }

    private static class MotionEventHook {

        final MotionEventHookCallback callback;

        MotionEventHook(MotionEventHookCallback callback) {
            this.callback = callback;
        }

        void hook(MotionEvent event, int action) {
            final int historyAction = event.getAction();
            event.setAction(action);
            callback.apply(event);
            event.setAction(historyAction);
        }
    }

    private interface MotionEventHookCallback {
        void apply(MotionEvent event);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        final ScrollableLayoutSavedState savedState = new ScrollableLayoutSavedState(superState);

        savedState.scrollY = getScrollY();

        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {

        if (!(state instanceof ScrollableLayoutSavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        final ScrollableLayoutSavedState in = (ScrollableLayoutSavedState) state;
        super.onRestoreInstanceState(in.getSuperState());

        setScrollY(in.scrollY);
    }

    private static class ScrollableLayoutSavedState extends BaseSavedState {

        int scrollY;

        public ScrollableLayoutSavedState(Parcel source) {
            super(source);

            scrollY = source.readInt();
        }

        public ScrollableLayoutSavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeInt(scrollY);
        }

        public static final Creator<ScrollableLayoutSavedState> CREATOR
                = new Creator<ScrollableLayoutSavedState>() {

            @Override
            public ScrollableLayoutSavedState createFromParcel(Parcel in) {
                return new ScrollableLayoutSavedState(in);
            }

            @Override
            public ScrollableLayoutSavedState[] newArray(int size) {
                return new ScrollableLayoutSavedState[size];
            }
        };
    }
}
