package com.github.fo2rist.mclaren;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Context context;

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() {
        context = activityRule.getActivity();
    }

    @Test
    public void testMainScreenLayout() {
        onView(withId(R.id.main_content_frame));
        onView(withId(R.id.fab)).check(matches(isDisplayed()));
    }

    @Test
    public void testSideMenu() {
        onView(withText("Newsfeed")).check(matches(not(isDisplayed())));

        openNavigationDrawer();

        onView(withText("Newsfeed")).check(matches(isDisplayed()));
        onView(withText("Drivers")).check(matches(isDisplayed()));
        onView(withText("Circuits")).check(matches(isDisplayed()));
        onView(withText("Car")).check(matches(isDisplayed()));
    }

    @Test
    public void testOptionsMenu() {
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        onView(withText("Settings")).check(matches(isDisplayed()));
    }

    @Test
    public void testNavigationToSamePage() {
        openNavigationDrawer();

        onView(withId(R.id.nav_view)).perform(navigateTo(R.id.nav_newsfeed));
    }

    @Test
    public void testNavigationToAllPages() throws InterruptedException {
        openNavigationDrawer();
        navigateToMenuItem(R.id.nav_drivers);
        openNavigationDrawer();
        navigateToMenuItem(R.id.nav_circuits);
        openNavigationDrawer();
        navigateToMenuItem(R.id.nav_car);

        //and back to the initial
        openNavigationDrawer();
        navigateToMenuItem(R.id.nav_newsfeed);
    }

    private void navigateToMenuItem(int menuItemId) throws InterruptedException {
        onView(withId(R.id.nav_view)).perform(navigateTo(menuItemId));
        sleep(1000);
    }

    private void openNavigationDrawer() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
    }
}
