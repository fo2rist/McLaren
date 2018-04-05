package com.github.fo2rist.mclaren.tests.utils;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.github.fo2rist.mclaren.BuildConfig;
import com.github.fo2rist.mclaren.utils.IntentUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IntentUtilsTest {

    @Test
    public void testExitingPackageResolved() throws Exception {
        Intent intent = IntentUtils.createAppIntent(InstrumentationRegistry.getTargetContext(),
                BuildConfig.APPLICATION_ID);

        assertEquals(BuildConfig.APPLICATION_ID, intent.getPackage());
        assertNull(intent.getData());
    }

    @Test
    public void testNonExistingPackageResolvedToBrowser() throws Exception {
        Intent intent = IntentUtils.createAppIntent(InstrumentationRegistry.getTargetContext(),
                "package_that_doesnt_exist");

        assertTrue(intent.getData().toString().startsWith("http"));
    }
}
