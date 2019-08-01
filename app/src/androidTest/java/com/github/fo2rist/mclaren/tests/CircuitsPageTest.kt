package com.github.fo2rist.mclaren.tests

import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.CircuitsPage
import com.github.fo2rist.mclaren.ui.circuitsscreen.CircuitDetailsActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CircuitsPageTest : BaseMainActivityTest() {

    private val circuitsPage = CircuitsPage()

    @Before
    fun setUp() {
        mainPage.navigateToMenuItem(R.id.nav_circuits)
    }

    @Test
    fun testTopItemPresent_andLaunchesDetails() {
        interceptIntents()

        circuitsPage {
            onCircuitsList.childAt<CircuitsPage.CircuitItem>(0) { click() }
        }

        intended(hasComponent(CircuitDetailsActivity::class.java.name))
    }

    @Test
    fun testBottomItemPresent_andLaunchesDetails() {
        interceptIntents()

        circuitsPage {
            onCircuitsList.childAt<CircuitsPage.CircuitItem>(20) { click() }
        }

        intended(hasComponent(CircuitDetailsActivity::class.java.name))
    }
}
