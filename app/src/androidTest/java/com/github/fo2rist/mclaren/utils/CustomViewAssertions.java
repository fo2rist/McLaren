package com.github.fo2rist.mclaren.utils;


import android.support.test.espresso.ViewAssertion;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

public class CustomViewAssertions {
    public static ViewAssertion displayed() {
        return matches(isDisplayed());
    }
}
