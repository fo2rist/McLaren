package com.github.fo2rist.mclaren;

import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.pages.CircuitsPage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

@RunWith(AndroidJUnit4.class)
public class CircuitsPageTest extends BaseMainActivityTest {
    private CircuitsPage circuitsPage = new CircuitsPage();

    @Override
    @Before
    public void setUp() throws InterruptedException {
        super.setUp();
        mainPage.navigateToMenuItem(R.id.nav_circuits);
    }

    @Test
    public void testFirstItemPresent() throws Exception {
        circuitsPage.onCircuitsList()
                .perform(actionOnItemAtPosition(0, click()));

    }

    @Test
    public void testLastItemPresent() throws Exception {
        circuitsPage.onCircuitsList()
                .perform(actionOnItemAtPosition(19, scrollTo()))
                .perform(actionOnItemAtPosition(19, click()));
    }

}
