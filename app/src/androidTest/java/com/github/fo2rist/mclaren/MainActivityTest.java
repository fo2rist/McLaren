package com.github.fo2rist.mclaren;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.DriversPage;
import com.github.fo2rist.mclaren.pages.MainPage;
import com.github.fo2rist.mclaren.pages.NewsfeedPage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private Context context;
    private MainPage mainPage = new MainPage();
    private NewsfeedPage newsfeedPage = new NewsfeedPage();
    private DriversPage driversPage = new DriversPage();

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        context = activityRule.getActivity();
    }

    @Test
    public void testMainScreenLayout() throws Exception {
        mainPage.onContentFrame()
                .check(displayed());
        mainPage.onToolbar()
                .check(displayed());
        mainPage.onFloatingButton()
                .check(displayed());
    }

    @Test
    public void testSideMenu() throws Exception {
        mainPage.onMenuNewsfeed()
                .check(matches(not(isDisplayed())));

        mainPage.openNavigationDrawer();

        mainPage.onMenuNewsfeed()
                .check(displayed());
        mainPage.onMenuCircuits()
                .check(displayed());
        mainPage.onMenuDrivers()
                .check(displayed());
        mainPage.onMenuCar()
                .check(displayed());
    }

    @Test
    public void testOptionsMenu() throws Exception {
        Espresso.openActionBarOverflowOrOptionsMenu(context);
        onView(withText("Settings"))
                .check(displayed());
    }

    @Test
    public void testNavigationToSamePage() throws Exception {
        newsfeedPage.onNewsList()
                .check(displayed());
        navigateToMenuItem(R.id.nav_newsfeed);
        newsfeedPage.onNewsList()
                .check(displayed());
    }

    @Test
    public void testDriversPages() throws Exception {
        navigateToMenuItem(R.id.nav_drivers);
        //Alonso present
        onView(withText("#FA14"))
                .check(displayed());
        onView(withText("Fernando Alonso"))
                .check(displayed());
        onView(withText("@alo_official"))
                .check(displayed());
        onView(withText("29.07.1981"))
                .check(displayed());
        onView(withText("Spanish"))
                .check(displayed());
        onView(withText("2"))
                .check(displayed());

        onView(withText("1st x 32"))
                .check(displayed());
        onView(withText("97"))
                .check(displayed());
        onView(withText("22"))
                .check(displayed());
        onView(withText("21"))
                .check(displayed());

        driversPage.onViewPager()
            .check(displayed())
            .perform(swipeLeft());
    }

    private void navigateToMenuItem(int menuItemId) throws InterruptedException {
        mainPage.openNavigationDrawer();
        mainPage.onNavigationView()
                .perform(navigateTo(menuItemId));
        sleep(1000);
    }

}
