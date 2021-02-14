package com.github.fo2rist.mclaren.ui.driversscreen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.common.views.KView
import com.github.fo2rist.mclaren.McLarenApplication
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepositoryImpl
import com.github.fo2rist.mclaren.testutilities.fakes.FakeRemoteConfigService
import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


private val REPOSITORY by lazy {
    DriversRepositoryImpl(FakeRemoteConfigService(
            drivers = """{ "NORRIS": { "Name": "Lando", "DateOfBirth": "01.02.2003", "Nationality": "Human", "Twitter": "@twitter", "FastestLaps": "1", "Tag": "TAG" } }""",
            driversOrderList = """["NORRIS"]"""))
}
private val DRIVER_ID = DriverId.NORRIS
private val DRIVER_MODEL: Driver by lazy { REPOSITORY.getDriver(DRIVER_ID) }

/**
 * Test view content population for [DriverSubFragment].
 */
@RunWith(AndroidJUnit4::class)
class DriverSubFragmentTest {
    private var app: McLarenApplication = ApplicationProvider.getApplicationContext()


    @Before
    fun setUp() {
        // force setting the theme is required for Floating button, for some reason theme from manifest is not applied
        app.setTheme(R.style.AppTheme)

        // here fragment itself is not required, only needed to fetch args bundle from it for launch
        launchFragmentInContainer<TestDriverSubFragment>(DriverSubFragment.newInstance(DRIVER_ID).arguments)
    }

    @Test
    fun test_layout_forActiveDriver_contains_Mandatory_and_Optional_fields() {

        val page = KView { withId(R.id.properties_linearlayout) }

        // Main fields present (both title and value)
        page.assertTitleAndValuePresentFor(DriverProperty.Name)
        page.assertTitleAndValuePresentFor(DriverProperty.DateOfBirth)
        page.assertTitleAndValuePresentFor(DriverProperty.Nationality)
        page.assertTitleAndValuePresentFor(DriverProperty.Twitter)

        // optional field present
        page.assertTitleAndValuePresentFor(DriverProperty.FastestLaps)

        // tag present
        page.assertHasViewWithText(DRIVER_MODEL[DriverProperty.Tag]!!)
    }

    private fun KView.assertTitleAndValuePresentFor(property: DriverProperty) {
        hasDescendant {
            withText(app.getString(property.nameResId))
        }
        hasDescendant {
            withText(DRIVER_MODEL[property]!!)
        }
    }

    private fun KView.assertHasViewWithText(text: String) {
        hasDescendant {
            withText(text)
        }
    }

    // test version of fragment which avoids android dagger and allows manual injection
    class TestDriverSubFragment : DriverSubFragment() {
        override fun injectDependencies() {
            this.driversRepository = REPOSITORY
        }
    }
}
