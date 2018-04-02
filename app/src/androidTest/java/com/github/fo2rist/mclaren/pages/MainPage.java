package com.github.fo2rist.mclaren.pages;


import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.DrawerActions;

import com.github.fo2rist.mclaren.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
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
        return onView(withId(R.id.floatig_button_transmission));
    }

    private ViewInteraction onNavigationView() {
        return onView(withId(R.id.nav_view_main));
    }

    private ViewInteraction onFooterNavigationView() {
        return onView(withId(R.id.nav_view_footer));
    }

    public ViewInteraction onMenuNewsFeed() {
        return onView(withText("Feed"));
    }

    public ViewInteraction onMenuStories() {
        return onView(withText("Stories"));
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

    public ViewInteraction onOptionMenuAbout() {
        return onView(withText("About"));
    }

    public void openNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    }

    public void navigateToMenuItem(int menuItemId) throws InterruptedException {
        openNavigationDrawer();
        onNavigationView()
                .perform(navigateTo(menuItemId));
    }

    public void navigateToFooterMenuItem(int menuItemId) throws InterruptedException {
        openNavigationDrawer();
        onFooterNavigationView()
                .perform(navigateTo(menuItemId));
    }

    public ViewInteraction onMenuOfficialSite() {
        return onView(withText("Official site"));
    }
}
