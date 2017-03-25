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
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;
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
        mainPage.navigateToMenuItem(R.id.nav_newsfeed);
        newsfeedPage.onNewsList()
                .check(displayed());
    }

    @Test
    public void testDriversPages() throws Exception {
        mainPage.navigateToMenuItem(R.id.nav_drivers);
        //Alonso present/ title etc
        onView(withText("#FA14"))
                .check(displayed());
        //all properties
        checkPropertyTextPresent("Fernando Alonso");
        checkPropertyTextPresent("@alo_official");
        checkPropertyTextPresent("29.07.1981");
        checkPropertyTextPresent("Spanish");
        checkPropertyTextPresent("2");

        checkPropertyTextPresent("1st x 32");
        checkPropertyTextPresent("97");
        checkPropertyTextPresent("22");
        checkPropertyTextPresent("21");

        //second driver present
        driversPage.onViewPager()
            .check(displayed())
            .perform(swipeLeft());
    }

    private void checkPropertyTextPresent(String text) {
        onView(withText(text))
                .perform(scrollTo())
                .check(displayed());
    }
}
