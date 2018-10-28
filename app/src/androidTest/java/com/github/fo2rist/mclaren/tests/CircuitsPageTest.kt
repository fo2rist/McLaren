package com.github.fo2rist.mclaren.tests

import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.CircuitsPage
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CircuitsPageTest : BaseMainActivityTest() {

    private val circuitsPage = CircuitsPage()

    @Before
    override fun setUp() {
        super.setUp()
        mainPage.navigateToMenuItem(R.id.nav_circuits)
    }

    @Test
    @Throws(Exception::class)
    fun testTopItemPresent() {
        circuitsPage {
            onCircuitsList.childAt<CircuitsPage.CircuitItem>(0) { click() }
        }
    }

    @Test
    @Throws(Exception::class)
    fun testBottomItemPresent() {
        circuitsPage {
            onCircuitsList.childAt<CircuitsPage.CircuitItem>(20) { click() }
        }
    }
}
