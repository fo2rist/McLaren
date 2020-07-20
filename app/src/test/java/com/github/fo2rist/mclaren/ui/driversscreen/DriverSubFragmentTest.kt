package com.github.fo2rist.mclaren.ui.driversscreen

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepositoryImpl
import com.github.fo2rist.mclaren.ui.models.Driver
import com.github.fo2rist.mclaren.ui.models.DriverId
import com.github.fo2rist.mclaren.ui.models.DriverProperty
import com.github.fo2rist.mclaren.web.remoteconfig.FirebaseRemoteConfigService
import com.google.firebase.FirebaseApp
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Test view content population for [DriverSubFragment].
 */
@RunWith(RobolectricTestRunner::class)
@Ignore("Need to support/avoid injection, simplest option — make class open, override injection and pass mock")
class DriverSubFragmentTest {
    private val driverForTest = DriverId.SAINZ
    private lateinit var driverModel: Driver

    private var context: Context = RuntimeEnvironment.application

    private lateinit var rootLayout: KView
    private lateinit var portraitImage: KImageView

    @Before
    fun setUp() {
        // in tests Firebase is not initialized by default, so have to do that manually
        FirebaseApp.initializeApp(context)

        // TODO ideally this test can be independent from Firebase if we can inject the drivers repo to the fragment
        //     then test can fully rely on its own test data, instead of full dagger initialization.
        //     another option would be to pass the full driver model as the parameter to the fragment
        val driversRepository = DriversRepositoryImpl(FirebaseRemoteConfigService())
        driverModel = driversRepository.getDriver(driverForTest)

        // here fragment itself is not required, only needed to fetch args bundle from it for launch
        val fragment = DriverSubFragment.newInstance(driverForTest)
        launchFragmentInContainer<DriverSubFragment>(fragment.arguments)

        // TODO use launchInContainer(DriverSubFragment.class) instead of old
        //  FragmentTestUtil.startFragment(fragment, MainActivity.class);
        //  Robolectric doesn't work well with androidx fragments
        rootLayout = KView { withId(R.id.properties_linearlayout) }
        portraitImage = KImageView { withId(R.id.driver_portrait_image) }
    }

    @Test
    fun test_layout_forActiveDriver_contains_Mandatory_and_Optional_fields() {
        //Main fields present (both title and value)
        assertTitleAndValuePresentFor(DriverProperty.Name)
        assertTitleAndValuePresentFor(DriverProperty.DateOfBirth)
        assertTitleAndValuePresentFor(DriverProperty.Nationality)
        assertTitleAndValuePresentFor(DriverProperty.Twitter)

        //options fields present
        assertTitleAndValuePresentFor(DriverProperty.PolePositions)
        assertTitleAndValuePresentFor(DriverProperty.FastestLaps)

        //tag present
        rootLayout {
            hasDescendant {
                withText(driverModel[DriverProperty.Tag]!!)
            }
        }
        //photo resolved
        portraitImage {
            hasDrawable(R.drawable.driver_portrait_sainz)
        }
    }

    private fun assertTitleAndValuePresentFor(property: DriverProperty) {
        rootLayout {
            hasDescendant {
                withText(context.getString(property.nameResId))
            }
            hasDescendant {
                withText(driverModel[property]!!)
            }
        }
    }
}
