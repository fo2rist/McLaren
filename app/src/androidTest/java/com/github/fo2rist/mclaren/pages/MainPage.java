package com.github.fo2rist.mclaren.pages;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class MainPage {
    public ViewInteraction onToolbar() {
        return onView(withId(R.id.toolbar));
    }

    public ViewInteraction onContentFrame() {
        return onView(withId(R.id.main_content_frame));
    }

    public ViewInteraction onFloatingButton() {
        return onView(withId(R.id.fab));
    }

    public ViewInteraction onNavigationView() {
        return onView(withId(R.id.nav_view));
    }

    public ViewInteraction onMenuNewsfeed() {
        return onView(withText("Newsfeed"));
    }

    public ViewInteraction onMenuCircuits() {
        return onView(withText("Circuits"));
    }

    public ViewInteraction onMenuDrivers() {
        return onView(withText("Drivers"));
    }

    public ViewInteraction onMenuCar() {
        return onView(withText("Car"));
    }

    public void openNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    }
}
