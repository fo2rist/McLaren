package com.github.fo2rist.mclaren.tests

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.net.Uri
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasData
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.pages.CircuitDetailsPage
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity
import com.github.fo2rist.mclaren.ui.models.CalendarEvent
import org.hamcrest.Matchers.any
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CircuitDetailsPageTest {

    @get:Rule
    var rule = IntentsTestRule(CircuitDetailsActivity::class.java, false, false)
    private val page = CircuitDetailsPage()
    private lateinit var context: Context

    @Before
    fun setUp() {
        this.context = InstrumentationRegistry.getTargetContext()
        launchActivity(EVENT)
    }

    private fun launchActivity(event: CalendarEvent) {
        rule.launchActivity(CircuitDetailsActivity.createIntent(context, event))
    }

    @Test
    fun testMonacoLayoutPresent() {
        page {
            title {
                isDisplayed()
                hasText(GP_NAME)
            }

            circuitImage {
                isDisplayed()
            }

            circuitDetails {
                isDisplayed()
                containsText(CITY)
                containsText(TRACK_NAME)
            }

            detailsItemLaps {
                scrollTo()
                withText("$LAPS")
            }

            detailsItemLength {
                scrollTo()
                withText(String.format("%.3f km", LENGTH))
            }

            detailsItemDistance {
                scrollTo()
                withText(String.format("%.3f km", DISTANCE))
            }

            detailsItemSeasons {
                scrollTo()
                withText(SEASONS)
            }

            detailsItemGpHeld {
                scrollTo()
                withText("$GP_HELD")
            }
        }
    }

    @Test
    fun testTitleInfoLeadToWebsite() {
        intending(hasData(any(Uri::class.java))).respondWith(ActivityResult(RESULT_OK, null))

        page.title { click() }

        intended(hasData(any(Uri::class.java)))
    }

    companion object TestData {

        private val ID = "monaco"
        private val COUNTRY_CODE = "CODE"
        private val TRACK_NAME = "track name"
        private val CITY = "City"
        private val GP_NAME = "GP name"
        private val LAPS = 11
        private val LENGTH = 12.34
        private val DISTANCE = 56.789
        private val SEASONS = "2001"
        private val GP_HELD = 1111
        private val WIKI_LINK = "http://wiki.link"
        private val START_DATE = DateTime(1, 1, 1, 0, 0)

        private val EVENT = CalendarEvent(ID,
                COUNTRY_CODE, TRACK_NAME, CITY, GP_NAME, LAPS, LENGTH, DISTANCE, SEASONS, GP_HELD, WIKI_LINK, START_DATE)
    }
}
