package com.github.fo2rist.mclaren

/**
 * Test app that helps to not initialize unnecessary production services under tests.
 * Robolectric automatically picks `Test+APP_CLASS_NAME` class unless the other is specified in the config.
 */
@Suppress("Registered")
class TestMcLarenApplication : McLarenApplication() {

    override fun isInTestMode() = true
}
