package com.github.fo2rist.mclaren.ui;

import android.app.Activity;
import android.app.Instrumentation;
import android.net.Uri;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.pages.NewsfeedPage;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.github.fo2rist.mclaren.testutilities.CustomViewAssertions.displayed;
import static org.hamcrest.Matchers.any;
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
                .check(matches(not(isDisplayed())));
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
        mainPage.onMenuOfficialSite()
                .check(displayed());
    }

    @Test
    public void testOptionsMenu() throws Exception {
        initViewIntents();

        openActionBarOverflowOrOptionsMenu(context);
        mainPage.onOptionMenuAbout()
                .check(displayed())
                .perform(click());

        intended(hasComponent(PreviewActivity.class.getName()));
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

    @Test
    public void testFooterMenuActionCar() throws Exception {
        initViewIntents();

        mainPage.navigateToFooterMenuItem(R.id.nav_car);

        intended(hasData(any(Uri.class)));
    }

    @Test
    public void testFooterMenuActionOfficialSite() throws Exception {
        initViewIntents();

        mainPage.navigateToFooterMenuItem(R.id.nav_official_site);

        intended(hasData(any(Uri.class)));
    }

    private void initViewIntents() {
        Intents.init();
        Intents.intending(hasAction(any(String.class)))
                .respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @After
    public void tearDown() throws Exception {
        try {
            Intents.release();
        } catch (Exception exc) {
            //we don't really care
        }
    }
}
