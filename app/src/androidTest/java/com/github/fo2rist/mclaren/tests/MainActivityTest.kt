package com.github.fo2rist.mclaren.tests

import android.app.Activity
import android.app.Instrumentation
import android.net.Uri
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.FeedPage
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseMainActivityTest() {

    @Test
    fun testSideMenu() {
        mainPage {
            menuNewsFeed { isNotDisplayed() }

            openNavigationDrawer()

            menuStories { isDisplayed() }
            menuCircuits { isDisplayed() }
            menuDrivers { isDisplayed() }
            menuNewsFeed { isDisplayed() }
            menuCar { isDisplayed() }
            menuOfficialSite { isDisplayed() }
        }
    }

    @Test
    fun testOptionsMenu() {
        initViewIntents()

        mainPage {
            openActionBarOverflowOrOptionsMenu(context)
            optionMenuAbout.click()
        }

        intended(hasComponent(PreviewActivity::class.java.name))
    }

    @Test
    fun testNavigationToSamePage() {
        val feedPage = FeedPage()

        feedPage.feedList.isDisplayed()

        mainPage.navigateToMenuItem(R.id.nav_stories)

        feedPage.feedList.isDisplayed()
    }

    @Test
    fun testFooterMenuActionCar() {
        initViewIntents()

        mainPage.navigateToFooterMenuItem(R.id.nav_car)

        intended(hasData(any(Uri::class.java)))
    }

    @Test
    fun testFooterMenuActionOfficialSite() {
        initViewIntents()

        mainPage.navigateToFooterMenuItem(R.id.nav_official_site)

        intended(hasData(any(Uri::class.java)))
    }

    private fun initViewIntents() {
        Intents.init()
        Intents.intending(hasAction(any(String::class.java)))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
    }

    @After
    fun tearDown() {
        try {
            Intents.release()
        } catch (exc: Exception) {
            //we don't really care
        }
    }
}
