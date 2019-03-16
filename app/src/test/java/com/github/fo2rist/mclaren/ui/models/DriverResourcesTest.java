package com.github.fo2rist.mclaren.ui.models;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.fo2rist.mclaren.R;
import com.github.fo2rist.mclaren.ui.driversscreen.DriverSubFragment;
import com.github.fo2rist.mclaren.ui.models.Driver.AdditionalProperty;
import com.github.fo2rist.mclaren.ui.models.Driver.MandatoryProperty;
import com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;

import static com.github.fo2rist.mclaren.testutilities.LayoutUtils.assertContainsViewWithText;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(ParameterizedRobolectricTestRunner.class)
public class DriverResourcesTest {

    @ParameterizedRobolectricTestRunner.Parameters(name = "Main Driver Fragment populated for {0}")
    public static List<DriverId[]> data() {
        return Arrays.asList(new DriverId[][]{{DriverId.SAINZ}, {DriverId.NORRIS}});
    }

    private DriverId driverId;

    private LinearLayout rootLayout;
    private ImageView portraitImage;
    private Driver driverModel;

    public DriverResourcesTest(DriverId parameter) {
        driverId = parameter;
    }

    @Before
    public void setUp() {
        driverModel = DriversFactory.getDriverModel(driverId);

        DriverSubFragment fragment = DriverSubFragment.newInstance(driverId);
        startFragment(fragment);

        rootLayout = fragment.getView().findViewById(R.id.properties_linearlayout);
        portraitImage = fragment.getView().findViewById(R.id.driver_portrait_image);
    }

    @Test
    public void testLayoutPopulated() {
        //Main fields present (both title and value)
        assertTitleAndValuePresentFor(MandatoryProperty.NAME);
        assertTitleAndValuePresentFor(MandatoryProperty.DATE_OF_BIRTH);
        assertTitleAndValuePresentFor(MandatoryProperty.NATIONALITY);
        assertTitleAndValuePresentFor(MandatoryProperty.TWITTER);

        //options fields present
        assertTitleAndValuePresentFor(AdditionalProperty.POLE_POSITIONS);
        assertTitleAndValuePresentFor(AdditionalProperty.FASTEST_LAPS);

        //tag present
        assertContainsViewWithText(rootLayout, driverModel.getProperty(AdditionalProperty.TAG));

        //photo resolved
        assertNotNull(portraitImage.getDrawable());
    }

    private void assertTitleAndValuePresentFor(Driver.Property property) {
        assertContainsViewWithText(rootLayout, property.getDisplayName());
        assertContainsViewWithText(rootLayout, driverModel.getProperty(property));
    }
}
