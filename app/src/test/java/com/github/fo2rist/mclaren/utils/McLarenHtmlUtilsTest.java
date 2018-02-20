package com.github.fo2rist.mclaren.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
public class McLarenHtmlUtilsTest {
    @Test
    public void testNullHandled() throws Exception {
        assertEquals("", McLarenHtmlUtils.pretify(null));
    }
}
