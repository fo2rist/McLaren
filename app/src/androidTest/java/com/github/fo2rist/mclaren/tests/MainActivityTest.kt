package com.github.fo2rist.mclaren.tests

import android.net.Uri
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.FeedPage
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import com.github.fo2rist.mclaren.ui.transmissionscreen.TransmissionActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.any
import org.joda.time.DateTime
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseMainActivityTest() {

    @Test
    fun testSideMenu() {
        mainPage {
            menuStories { isNotDisplayed() }

            openNavigationDrawer()

            menuStories { isDisplayed() }
            menuTeamTwitter { isDisplayed() }
            menuCircuits { isDisplayed() }
            menuDrivers { isDisplayed() }
            menuCar { isDisplayed() }
            menuOfficialSite { isDisplayed() }
        }
    }

    @Test
    fun testFloatingButtonUpcomingEvent() {
        val upcomingEventName = "TEST UPCOMING GP"
        val upcomingEventTime = DateTime().plusMinutes(61)
        interceptIntents()
        activity.runOnUiThread { activity.showUpcomingEventButton(upcomingEventName, upcomingEventTime) }

        mainPage {
            upcomingEventButton {
                isDisplayed()
                matches {
                    containsText(upcomingEventName)
                    containsText("1H")
                }
                perform { click() }
            }
        }
        intended(hasComponent(CircuitDetailsActivity::class.java.name))
    }

    @Test
    fun testFloatingButtonTransmission() {
        interceptIntents()
        activity.runOnUiThread { activity.showTransmissionButton() }
        mainPage {
            transmissionButton {
                isDisplayed()
                perform { click() }
            }
        }
        intended(hasComponent(TransmissionActivity::class.java.name))
    }

    @Test
    fun testOptionsMenu() {
        interceptIntents()

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
        interceptIntents()

        mainPage.navigateToFooterMenuItem(R.id.nav_car)

        intended(hasData(any(Uri::class.java)))
    }

    @Test
    fun testFooterMenuActionOfficialSite() {
        interceptIntents()

        mainPage.navigateToFooterMenuItem(R.id.nav_official_site)

        intended(hasData(any(Uri::class.java)))
    }
}
