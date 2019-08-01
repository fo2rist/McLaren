package com.github.fo2rist.mclaren.tests

import android.support.test.runner.AndroidJUnit4
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
            driverNumber.hasText("#CS55")
            driverPageLink.isDisplayed()

            infoItemWithTitle(context.getString(DriverProperty.Name.nameResId))
                    .hasText("Carlos Sainz")
            infoItemWithTitle(context.getString(DriverProperty.Twitter.nameResId))
                    .hasText("@Carlossainz55")
            infoItemWithTitle(context.getString(DriverProperty.DateOfBirth.nameResId))
                    .hasText("01.09.1994")
            infoItemWithTitle(context.getString(DriverProperty.Nationality.nameResId))
                    .hasText("Spanish")

            infoItemWithTitle(context.getString(DriverProperty.BestFinish.nameResId))
                    .hasText("4th")
            infoItemWithTitle(context.getString(DriverProperty.PolePositions.nameResId))
                    .hasText("0")
            infoItemWithTitle(context.getString(DriverProperty.FastestLaps.nameResId))
                    .hasText("0")
        }
    }

    @Test
    fun testSecondPagePresent() {
        driversPage.swipeLeft()
    }
}
