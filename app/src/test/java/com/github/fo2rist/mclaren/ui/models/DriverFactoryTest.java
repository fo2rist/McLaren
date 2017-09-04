package com.github.fo2rist.mclaren.ui.models;

import com.github.fo2rist.mclaren.ui.models.DriversFactory.DriverId;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DriverFactoryTest {

    @Parameters(name = "Driver: {0}")
    public static List<DriverId> data() {
        return Arrays.asList(DriverId.values());
    }

    @Parameter(0)
    public DriverId driverParam;

    @Test
    public void testDriverCreated() throws Exception {
        Driver driver = DriversFactory.getDriverModel(driverParam);

        assertMandatoryFieldsPresent(driver);
        assertIdPresent(driver);
    }

    private void assertMandatoryFieldsPresent(Driver driver) {
        for (Driver.MandatoryProperty property: Driver.MandatoryProperty.values()) {
            assertNotNull(driver.getProperty(property));
            assertFalse(driver.getProperty(property).isEmpty());
        }
    }

    private void assertIdPresent(Driver driver) {
        assertNotNull(driver.getId());
        assertFalse(driver.getId().isEmpty());
    }
}
