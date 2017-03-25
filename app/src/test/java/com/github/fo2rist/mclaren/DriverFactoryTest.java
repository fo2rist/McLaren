package com.github.fo2rist.mclaren;

import com.github.fo2rist.mclaren.models.Driver;
import com.github.fo2rist.mclaren.models.DriversFactory;
import com.github.fo2rist.mclaren.models.DriversFactory.DriverId;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;
import java.util.List;

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
        }
    }

    private void assertIdPresent(Driver driver) {
        assertNotNull(driver.getId());
    }
}
