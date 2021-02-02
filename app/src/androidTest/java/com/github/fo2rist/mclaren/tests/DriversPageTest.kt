package com.github.fo2rist.mclaren.tests

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.pages.DriversPage
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DriversPageTest : BaseMainActivityTest() {

    private val driversPage = DriversPage()

    @Before
    fun setUp() {
        mainPage.navigateToMenuItem(R.id.nav_drivers)
    }

    @Test
    fun testFirstPageContent() {
        driversPage {
            driverNumber.hasText("#LN4")
            driverPageLink.isDisplayed()

            infoItemWithTitle(context.getString(DriverProperty.Name.nameResId))
                    .hasText("Lando Norris")
            infoItemWithTitle(context.getString(DriverProperty.Twitter.nameResId))
                    .hasText("@LandoNorris")
            infoItemWithTitle(context.getString(DriverProperty.DateOfBirth.nameResId))
                    .hasText("13.11.1999")
            infoItemWithTitle(context.getString(DriverProperty.Nationality.nameResId))
                    .hasText("British")

            infoItemWithTitle(context.getString(DriverProperty.BestFinish.nameResId))
                    .isVisible()
            infoItemWithTitle(context.getString(DriverProperty.PolePositions.nameResId))
                    .isVisible()
            infoItemWithTitle(context.getString(DriverProperty.FastestLaps.nameResId))
                    .isVisible()
        }
    }

    @Test
    fun testSecondPagePresent() {
        driversPage.swipeLeft()
    }
}
