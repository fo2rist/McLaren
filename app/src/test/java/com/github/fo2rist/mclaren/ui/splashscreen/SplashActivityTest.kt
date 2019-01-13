package com.github.fo2rist.mclaren.ui.splashscreen

import com.github.fo2rist.mclaren.ui.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class SplashActivityTest {

    @Test
    fun `test Splash screen starts Main and finishes immediately`() {
        val splashActivity = Robolectric.buildActivity(SplashActivity::class.java).setup().get()

        assertTrue(splashActivity.isFinishing)
        assertEquals(MainActivity::class.java.canonicalName,
                shadowOf(splashActivity).nextStartedActivity.component?.className)
    }
}
