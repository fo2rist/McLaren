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
import com.github.fo2rist.mclaren.models.Circuit
import com.github.fo2rist.mclaren.models.Event
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

    private lateinit var context: Context
    private val page = CircuitDetailsPage()

    @Before
    fun setUp() {
        this.context = InstrumentationRegistry.getTargetContext()
        launchActivity()
    }

    private fun launchActivity() {
        rule.launchActivity(CircuitDetailsActivity.createIntent(context, 0))
    }

    @Test
    fun testFirstGpLayoutPresent() {
        page {
            title {
                isDisplayed()
                hasText("Australian Grand Prix") // usually the first GP each year
            }

            circuitImage {
                isDisplayed()
            }

            circuitDetails {
                isDisplayed()
                containsText("Melbourne")
                containsText(" >> ")     //GP name and location separator
                containsText("Melbourne Grand Prix Circuit")
            }

            detailsItemLaps {
                scrollTo()
                hasText("58")
            }

            detailsItemLength {
                scrollTo()
                hasText("5.303 km")
            }

            detailsItemDistance {
                scrollTo()
                hasText("307.574 km")
            }

            detailsItemSeasons {
                scrollTo()
                containsText("1996")
            }

            detailsItemGpHeld {
                scrollTo()
                hasAnyText()
            }
        }
    }

    @Test
    fun testTitleInfoLeadToWebsite() {
        intending(hasData(any(Uri::class.java))).respondWith(ActivityResult(RESULT_OK, null))

        page.title { click() }

        intended(hasData(any(Uri::class.java)))
    }

    @Test
    fun testCanSwipeToNextCircuit() {
        page.swipeLeft()
    }
}
