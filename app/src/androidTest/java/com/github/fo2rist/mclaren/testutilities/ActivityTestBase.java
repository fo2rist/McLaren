package com.github.fo2rist.mclaren.testutilities;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;

public class ActivityTestBase {
    protected Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
