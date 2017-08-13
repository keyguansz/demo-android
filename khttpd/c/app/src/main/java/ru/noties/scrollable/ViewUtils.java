package ru.noties.scrollable;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Dimitry Ivanov on 13.04.2016.
 */
class ViewUtils {
// KWillRefactor: 2017/6/25 系统不是有了么？ 
    private ViewUtils() {}

    static void removeGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {

        final ViewTreeObserver observer = view.getViewTreeObserver();
        if (!observer.isAlive()) {
            return;
        }

        if (Build.VERSION.SDK_INT >= 16) {
            observer.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            //noinspection deprecation
            observer.removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }
    }
}
