package com.github.fo2rist.mclaren.pages;

import android.support.test.espresso.ViewInteraction;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PreviewPage {
    public ViewInteraction onToolbarImage() {
        return onView(withId(R.id.image_header));
    }

    public ViewInteraction onWebView() {
        return onView(withId(R.id.web_view));
    }

    public ViewInteraction onGalleyView() {
        return onView(withId(R.id.images_pager));
    }
}
