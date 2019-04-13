package com.github.fo2rist.mclaren.tests;

import com.github.fo2rist.mclaren.repository.DriversRepositoryImpl;
import com.github.fo2rist.mclaren.ui.models.DriverId;
import com.github.fo2rist.mclaren.ui.models.DriverProperty;
import com.github.fo2rist.mclaren.ui.models.Driver;
import com.github.fo2rist.mclaren.web.FirebaseRemoteConfigService;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.runners.Parameterized.Parameters;

/**
 * Test default values in driver repo for real app.
 */
@RunWith(Parameterized.class)
public class DriversRepositoryIntegrationTest {

    @Parameters(name = "Driver: {0}")
    public static List<DriverId> data() {
        return Arrays.asList(DriverId.values());
    }

    @Parameter
    public DriverId driverParam;

    private DriversRepositoryImpl driversRepository = new DriversRepositoryImpl(new FirebaseRemoteConfigService());

    @Test
    public void testAllMandatoryFieldsPresent() {
        Driver driver = driversRepository.getDriver(driverParam);

        assertMandatoryFieldsPresent(driver);
    }

    private void assertMandatoryFieldsPresent(Driver driver) {
        for (DriverProperty property: DriverProperty.values()) {
            if (!property.isMandatory()) {
                continue;
            }
            assertNotNull(driver.get(property));
            assertFalse(driver.get(property).isEmpty());
        }
    }
}
