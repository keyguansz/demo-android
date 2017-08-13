package ru.noties.scrollable;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

/**
 * @author trey.cai
 * @date 2017-04-11
 */
final class SmoothScroller extends OverScroller {

    // Fling friction
    private float mFlingFriction = ViewConfiguration.getScrollFriction();
    // A context-specific coefficient adjusted to physical values.
    private float mPhysicalCoeff;

    private static float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
    private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)

    public SmoothScroller(Context context) {
        super(context);
        init(context);
    }

    public SmoothScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        init(context);
    }

    public SmoothScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY) {
        super(context, interpolator, bounceCoefficientX, bounceCoefficientY);
        init(context);
    }

    public SmoothScroller(Context context, Interpolator interpolator, float bounceCoefficientX, float bounceCoefficientY, boolean flywheel) {
        super(context, interpolator, bounceCoefficientX, bounceCoefficientY, flywheel);
        init(context);
    }

    private void init(Context context) {
        final float ppi = context.getResources().getDisplayMetrics().density * 160.0f;
        mPhysicalCoeff = SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f; // look and feel tuning
    }

    private double getSplineDeceleration(float velocity) {
        return Math.log(INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff));
    }

    int getSplineFlingDuration(float velocity) {
        final double l = getSplineDeceleration(velocity);
        final double decelMinusOne = DECELERATION_RATE - 1.0;
        return (int) (1000.0 * Math.exp(l / decelMinusOne));
    }
}
