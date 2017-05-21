package com.github.fo2rist.mclaren;

import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.NewsfeedPage;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends BaseMainActivityTest {

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
        NewsfeedPage newsfeedPage = new NewsfeedPage();
        newsfeedPage.onNewsList()
                .check(displayed());
        mainPage.navigateToMenuItem(R.id.nav_newsfeed);
        newsfeedPage.onNewsList()
                .check(displayed());
    }

}
