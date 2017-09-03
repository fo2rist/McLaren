package com.github.fo2rist.mclaren.ui;

import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.pages.DriversPage;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.fo2rist.mclaren.utilities.CustomViewAssertions.displayed;


@RunWith(AndroidJUnit4.class)
public class DriversPageTest extends BaseMainActivityTest {

    private DriversPage driversPage = new DriversPage();

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
