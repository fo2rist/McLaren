package com.github.fo2rist.mclaren.utilities;


import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class LayoutUtils {

    public static void assertContainsViewWithText(View rootView, CharSequence text) {
        List<View> views = findViewsWithText(rootView, text);
        assertTrue("Expected view with text '" + text + "' in parent view: " + rootView,
                views.size() > 0);
    }

    public static List<View> findViewsWithText(View rootView, CharSequence text) {
        ArrayList<View> outViews = new ArrayList<>();
        rootView.findViewsWithText(outViews, text, View.FIND_VIEWS_WITH_TEXT);
        return outViews;
    }
}
