package com.github.fo2rist.mclaren.utilities;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.web.sugar.Web.onWebView;

/**
 * Activity Test Rule that enables JS on Web View to allow its content testing.
 */
public class WebActivityTestRule<T extends Activity> extends ActivityTestRule<T> {
    public WebActivityTestRule(Class<T> activityClass) {
        super(activityClass);
    }

    public WebActivityTestRule(Class<T> activityClass, boolean initialTouchMode) {
        super(activityClass, initialTouchMode);
    }

    public WebActivityTestRule(Class<T> activityClass, boolean initialTouchMode, boolean launchActivity) {
        super(activityClass, initialTouchMode, launchActivity);
    }

    @Override
    protected void afterActivityLaunched() {
        super.afterActivityLaunched();
        onWebView().forceJavascriptEnabled();
    }
}
