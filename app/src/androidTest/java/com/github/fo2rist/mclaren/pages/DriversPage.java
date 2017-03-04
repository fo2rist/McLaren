package com.github.fo2rist.mclaren.pages;


import android.support.test.espresso.ViewInteraction;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class DriversPage {
    public ViewInteraction onViewPager() {
        return onView(withId(R.id.drivers_pager));
    }
}
