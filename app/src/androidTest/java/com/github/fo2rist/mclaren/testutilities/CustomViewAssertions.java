package com.github.fo2rist.mclaren.testutilities;


import androidx.test.espresso.ViewAssertion;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

public class CustomViewAssertions {
    public static ViewAssertion displayed() {
        return matches(isDisplayed());
    }
}
