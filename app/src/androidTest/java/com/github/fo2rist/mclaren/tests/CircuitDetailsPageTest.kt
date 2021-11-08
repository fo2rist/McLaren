package com.github.fo2rist.mclaren.tests

import android.app.Activity.RESULT_OK
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasData
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.pages.CircuitDetailsPage
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity
import org.hamcrest.Matchers.any
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
        this.context = InstrumentationRegistry.getInstrumentation().targetContext
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
                hasText("Bahrain Grand Prix") // usually the first GP each year is Australian, but not 2021
            }

            circuitImage {
                isDisplayed()
            }

            circuitDetails {
                isDisplayed()
                containsText("Sakhir")
                containsText(" >> ")     //GP name and location separator
                containsText("Bahrain International Circuit")
            }

            detailsItemLaps {
                scrollTo()
                hasText("57")
            }

            detailsItemLength {
                scrollTo()
                hasText("5.412 km")
            }

            detailsItemDistance {
                scrollTo()
                hasText("308.238 km")
            }

            detailsItemSeasons {
                scrollTo()
                containsText("2020")
            }

            detailsItemGpHeld {
                scrollTo()
                hasAnyText()
            }
        }
    }

    @Test
    fun testTitleInfoLeadsToWebsite() {
        intending(hasData(any(Uri::class.java))).respondWith(ActivityResult(RESULT_OK, null))

        page.title { click() }

        intended(hasData(any(Uri::class.java)))
    }

    @Test
    fun testCanSwipeToNextCircuit() {
        page.swipeLeft()
    }
}
