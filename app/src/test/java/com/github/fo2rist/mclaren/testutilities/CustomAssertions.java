package com.github.fo2rist.mclaren.testutilities;

import static junit.framework.Assert.assertTrue;

public class CustomAssertions {
    public static void assertStartsWith(String expected, String actual) {
        assertTrue(String.format("Assert\n\t`%s`\n\nshould start with\n\n\t`%s`", actual, expected),
                actual.startsWith(expected));
    }
}
