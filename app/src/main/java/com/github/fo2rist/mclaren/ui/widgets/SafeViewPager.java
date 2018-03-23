package com.github.fo2rist.mclaren.ui.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Same view pager as {@link ViewPager} that ignores touch event exceptions.
 * Since there is a bug that cause crashes when {@link com.github.chrisbanes.photoview.PhotoView} embedded
 * into ViewPager we need this interceptor to prevent crashes before either of two is fixed.
 */
public class SafeViewPager extends ViewPager {
    public SafeViewPager(@NonNull Context context) {
        super(context);
    }

    public SafeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException exc) {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException exc) {
            return false;
        }
    }
}
