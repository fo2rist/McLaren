package com.github.fo2rist.mclaren.ui.widgets;
/*
 * Copyright (C) 2015 takahirom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * WebView that implements {@link NestedScrollingChild} and so can notify toolbar when it need to be hidden.
 */
public class NestedWebView extends WebView implements NestedScrollingChild {
    private int lastY;
    private final int[] scrollOffset = new int[2];
    private final int[] scrollConsumed = new int[2];
    private int nestedOffsetY;
    private NestedScrollingChildHelper childHelper;

    public NestedWebView(Context context) {
        this(context, null);
    }

    public NestedWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    public NestedWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        childHelper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean returnValue = false;

        MotionEvent event = MotionEvent.obtain(ev);
        final int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            nestedOffsetY = 0;
        }
        int eventY = (int) event.getY();
        event.offsetLocation(0, nestedOffsetY);
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = lastY - eventY;
                // NestedPreScroll
                if (dispatchNestedPreScroll(0, deltaY, scrollConsumed, scrollOffset)) {
                    deltaY -= scrollConsumed[1];
                    lastY = eventY - scrollOffset[1];
                    event.offsetLocation(0, -scrollOffset[1]);
                    nestedOffsetY += scrollOffset[1];
                }
                returnValue = super.onTouchEvent(event);

                // NestedScroll
                if (dispatchNestedScroll(0, scrollOffset[1], 0, deltaY, scrollOffset)) {
                    event.offsetLocation(0, scrollOffset[1]);
                    nestedOffsetY += scrollOffset[1];
                    lastY -= scrollOffset[1];
                }
                break;
            case MotionEvent.ACTION_DOWN:
                returnValue = super.onTouchEvent(event);
                lastY = eventY;
                // start NestedScroll
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                returnValue = super.onTouchEvent(event);
                // end NestedScroll
                stopNestedScroll();
                break;
        }
        return returnValue;
    }

    // Nested Scroll implements
    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        childHelper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return childHelper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return childHelper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        childHelper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return childHelper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,
            int[] offsetInWindow) {
        return childHelper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return childHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return childHelper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return childHelper.dispatchNestedPreFling(velocityX, velocityY);
    }

}

