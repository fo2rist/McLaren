package com.github.fo2rist.mclaren.tests

import android.support.test.runner.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.DriversPage
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.BEST_FINISH
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.FASTEST_LAPS
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.PODIUMS
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.POLE_POSITIONS
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty.WORLD_CHAMPIONSHIPS
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
            driverNumber.hasText("#FA14")
            driverPageLink.isDisplayed()

            infoItemWithTitle(NAME.displayName)
                    .hasText("Fernando Alonso")
            infoItemWithTitle(TWITTER.displayName)
                    .hasText("@alo_official")
            infoItemWithTitle(DATE_OF_BIRTH.displayName)
                    .hasText("29.07.1981")
            infoItemWithTitle(NATIONALITY.displayName)
                    .hasText("Spanish")

            infoItemWithTitle(WORLD_CHAMPIONSHIPS.displayName)
                    .hasText("2")
            infoItemWithTitle(BEST_FINISH.displayName)
                    .hasText("1st x 32")
            infoItemWithTitle(PODIUMS.displayName)
                    .hasText("97")
            infoItemWithTitle(POLE_POSITIONS.displayName)
                    .hasText("22")
            infoItemWithTitle(FASTEST_LAPS.displayName)
                    .hasText("23")
        }

    }

    @Test
    fun testSecondPagePresent() {
        driversPage.swipeLeftAndWait()

        driversPage {
            driverNumber.hasText("#SV2")
            driverPageLink.isDisplayed()
        }
    }
}
