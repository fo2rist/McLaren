package com.github.fo2rist.mclaren.tests

import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.DriversPage
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.BEST_FINISH
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.FASTEST_LAPS
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.POLE_POSITIONS
import com.github.fo2rist.mclaren.ui.models.Driver.MandatoryProperty.DATE_OF_BIRTH
import com.github.fo2rist.mclaren.ui.models.Driver.MandatoryProperty.NAME
import com.github.fo2rist.mclaren.ui.models.Driver.MandatoryProperty.NATIONALITY
import com.github.fo2rist.mclaren.ui.models.Driver.MandatoryProperty.TWITTER
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DriversPageTest : BaseMainActivityTest() {

    private val driversPage = DriversPage()

    override fun setUp() {
        super.setUp()
        mainPage.navigateToMenuItem(R.id.nav_drivers)
    }

    @Test
    fun testFirstPageContent() {
        driversPage {
            driverNumber.hasText("#CS55")
            driverPageLink.isDisplayed()

            infoItemWithTitle(NAME.displayName)
                    .hasText("Carlos Sainz")
            infoItemWithTitle(TWITTER.displayName)
                    .hasText("@Carlossainz55")
            infoItemWithTitle(DATE_OF_BIRTH.displayName)
                    .hasText("01.09.1994")
            infoItemWithTitle(NATIONALITY.displayName)
                    .hasText("Spanish")

            infoItemWithTitle(BEST_FINISH.displayName)
                    .hasText("4th")
            infoItemWithTitle(POLE_POSITIONS.displayName)
                    .hasText("0")
            infoItemWithTitle(FASTEST_LAPS.displayName)
                    .hasText("0")
        }
    }

    @Test
    fun testSecondPagePresent() {
        driversPage.swipeLeft()
    }
}
