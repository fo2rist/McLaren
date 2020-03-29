package com.github.fo2rist.mclaren.ui.driversscreen;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepositoryImpl;
import com.github.fo2rist.mclaren.ui.MainActivity;
import com.github.fo2rist.mclaren.ui.models.Driver;
import com.github.fo2rist.mclaren.ui.models.DriverId;
import com.github.fo2rist.mclaren.ui.models.DriverProperty;
import com.github.fo2rist.mclaren.web.remoteconfig.FirebaseRemoteConfigService;
import com.google.firebase.FirebaseApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static com.github.fo2rist.mclaren.testutilities.LayoutUtils.assertContainsViewWithText;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Test view content population for {@link DriverSubFragment}.
 */
@RunWith(RobolectricTestRunner.class)
public class DriverSubFragmentTest {

    private DriverId driverForTest = DriverId.SAINZ;

    private Context context;
    private LinearLayout rootLayout;
    private ImageView portraitImage;
    private Driver driverModel;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.application;
        // in tests Firebase is not initialized by default, so have to do that manually
        FirebaseApp.initializeApp(context);

        //TODO ideally this test can be independent from Firebase if we can inject the drivers repo to the fragment
        //     then test can fully rely on its own test data, instead of full dagger initialization.
        //     another option would be to pass the full driver model as the parameter to the fragment
        DriversRepositoryImpl driversRepository = new DriversRepositoryImpl(new FirebaseRemoteConfigService());
        driverModel = driversRepository.getDriver(driverForTest);

        DriverSubFragment fragment = DriverSubFragment.newInstance(driverForTest);
        startFragment(fragment, MainActivity.class);

        rootLayout = fragment.getView().findViewById(R.id.properties_linearlayout);
        portraitImage = fragment.getView().findViewById(R.id.driver_portrait_image);
    }

    @Test
    public void test_layout_forActiveDriver_contains_Mandatory_and_Optional_fields() {
        //Main fields present (both title and value)
        assertTitleAndValuePresentFor(DriverProperty.Name);
        assertTitleAndValuePresentFor(DriverProperty.DateOfBirth);
        assertTitleAndValuePresentFor(DriverProperty.Nationality);
        assertTitleAndValuePresentFor(DriverProperty.Twitter);

        //options fields present
        assertTitleAndValuePresentFor(DriverProperty.PolePositions);
        assertTitleAndValuePresentFor(DriverProperty.FastestLaps);

        //tag present
        assertContainsViewWithText(rootLayout, driverModel.get(DriverProperty.Tag));

        //photo resolved
        assertNotNull(portraitImage.getDrawable());
    }

    private void assertTitleAndValuePresentFor(DriverProperty property) {
        assertContainsViewWithText(rootLayout, context.getString(property.getNameResId()));
        assertContainsViewWithText(rootLayout, driverModel.get(property));
    }
}
