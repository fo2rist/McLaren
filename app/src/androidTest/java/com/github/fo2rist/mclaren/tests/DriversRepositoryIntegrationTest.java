package com.github.fo2rist.mclaren.tests;

import com.github.fo2rist.mclaren.repository.remoteconfig.DriversRepositoryImpl;
import com.github.fo2rist.mclaren.ui.models.Driver;
import com.github.fo2rist.mclaren.ui.models.DriverId;
import com.github.fo2rist.mclaren.ui.models.DriverProperty;
import com.github.fo2rist.mclaren.web.remoteconfig.FirebaseRemoteConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

/**
 * Test default values in driver repo for real app.
 */
@RunWith(JUnit4.class)
public class DriversRepositoryIntegrationTest {

    private DriversRepositoryImpl driversRepository = new DriversRepositoryImpl(new FirebaseRemoteConfigService());

    @Test
    public void testAllMandatoryFieldsPresent() {
        for (DriverId driverId: driversRepository.getDriversList()) {
            Driver driver = driversRepository.getDriver(driverId);

            assertMandatoryFieldsPresent(driver);
        }
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
