package com.github.fo2rist.mclaren;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import com.github.fo2rist.mclaren.pages.MainPage;

import org.junit.Before;
import org.junit.Rule;

public class BaseMainActivityTest {
    Context context;
    MainPage mainPage;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        context = activityRule.getActivity();
        mainPage = new MainPage();
    }

}
