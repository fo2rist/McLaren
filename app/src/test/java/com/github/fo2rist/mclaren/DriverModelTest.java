package com.github.fo2rist.mclaren;

import com.github.fo2rist.mclaren.models.Driver;
import com.github.fo2rist.mclaren.models.Driver.MandatoryProperty;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class DriverModelTest {

    @Test(expected = IllegalArgumentException.class)
    public void testThrowsExceptionOnEmptyProperties() throws Exception {
        Map<String, String> emptyProperties = new HashMap<>();
        new Driver("any_id", emptyProperties);
    }

    @Test
    public void testConstructedWithMandatoryProperties() throws Exception {
        Map<String, String> completeProperties = new HashMap<>();
        for (MandatoryProperty prop: MandatoryProperty.values()) {
            completeProperties.put(prop.getDisplayName(), "anything");
        }
        new Driver("any_id", completeProperties);
    }
}