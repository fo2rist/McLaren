package com.github.fo2rist.mclaren.ui.driversscreen

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agoda.kakao.common.views.KView
import com.github.fo2rist.mclaren.McLarenApplication
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepositoryImpl
import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import com.github.fo2rist.mclaren.web.remoteconfig.FirebaseRemoteConfigService
import com.google.firebase.FirebaseApp
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

// TODO ideally this test can be independent from Firebase if we can inject the drivers repo to the fragment
private val repository by lazy { DriversRepositoryImpl(FirebaseRemoteConfigService()) }

private val DRIVER_ID = DriverId.SAINZ
private val DRIVER_MODEL: Driver by lazy { repository.getDriver(DRIVER_ID) }

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
        // in tests Firebase is not initialized by default, so have to do that manually
        FirebaseApp.initializeApp(app)

        // here fragment itself is not required, only needed to fetch args bundle from it for launch
        val fragment = DriverSubFragment.newInstance(DRIVER_ID)
        launchFragmentInContainer<TestDriverSubFragment>(fragment.arguments)
    }

    @Test
    fun test_layout_forActiveDriver_contains_Mandatory_and_Optional_fields() {
        val page = KView { withId(R.id.properties_linearlayout) }

        // Main fields present (both title and value)
        page.assertTitleAndValuePresentFor(DriverProperty.Name)
        page.assertTitleAndValuePresentFor(DriverProperty.DateOfBirth)
        page.assertTitleAndValuePresentFor(DriverProperty.Nationality)
        page.assertTitleAndValuePresentFor(DriverProperty.Twitter)

        // optional fields present
        page.assertTitleAndValuePresentFor(DriverProperty.PolePositions)
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
            this.driversRepository = repository
        }
    }
}
