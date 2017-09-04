package com.github.fo2rist.mclaren.pages;

import android.support.test.espresso.ViewInteraction;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CircuitDetailsPage {
    public ViewInteraction onCircuitTitle() {
        return onView(withId(R.id.circuit_title));
    }

    public ViewInteraction onCircuitImage() {
        return onView(withId(R.id.circuit_image));
    }

    public ViewInteraction onCircuitDetails() {
        return onView(withId(R.id.circuit_details));
    }
}
