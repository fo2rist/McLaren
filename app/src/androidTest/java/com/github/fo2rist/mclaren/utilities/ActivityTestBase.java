package com.github.fo2rist.mclaren.utilities;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

public class ActivityTestBase {
    protected Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getTargetContext();
    }
}
