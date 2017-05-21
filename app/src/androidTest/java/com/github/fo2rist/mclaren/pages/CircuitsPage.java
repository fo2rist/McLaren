package com.github.fo2rist.mclaren.pages;

import android.support.test.espresso.ViewInteraction;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CircuitsPage {
    public ViewInteraction onCircuitsList() {
        return onView(withId(R.id.list_circuits));
    }
}
