package com.github.fo2rist.mclaren.utils

import android.content.Intent
import com.github.fo2rist.mclaren.BuildConfig
import org.hamcrest.Matchers.startsWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.Shadows.shadowOf

private const val EXISTING_PACKAGE = BuildConfig.APPLICATION_ID
private const val NOT_EXISTING_PACKAGE = "package_that_doesnt_exist"
private const val DUMMY_URL = "http://dummy.url"

@RunWith(RobolectricTestRunner::class)
class IntentUtilsTest {

    private val context by lazy { RuntimeEnvironment.application.applicationContext }

    @Test
    fun `test createBrowserIntent resolves correct URL`() {
        val intent = IntentUtils.createBrowserIntent(DUMMY_URL)

        assertThat(intent.data!!.toString(), startsWith(DUMMY_URL))
        assertEquals(Intent.ACTION_VIEW, intent.action)
    }

    @Test
    fun `test createBrowserIntent trims spaces on correct URL`() {
        val intent = IntentUtils.createBrowserIntent(" $DUMMY_URL ")

        assertThat(intent.data!!.toString(), startsWith(DUMMY_URL))
    }

    @Test
    fun `test createBrowserIntent ignores missing schema`() {
        val intent = IntentUtils.createBrowserIntent(DUMMY_URL.removePrefix("http://"))

        assertThat(intent.data!!.toString(), startsWith(DUMMY_URL))
    }

    @Test
    fun `test launchSafely launches existing app`() {
        val launched = IntentUtils.launchSafely(context, IntentUtils.createAppIntent(context, EXISTING_PACKAGE))

        assertTrue(launched)
        assertEquals(EXISTING_PACKAGE,
                shadowOf(RuntimeEnvironment.application).nextStartedActivity.component!!.packageName)
    }

    @Test
    fun `test launchSafely doesn't launch not-existing app`() {
        val launched = IntentUtils.launchSafely(context, IntentUtils.createAppIntent(context, NOT_EXISTING_PACKAGE))

        assertFalse(launched)
    }

    @Test
    fun `test createAppIntent resolves exiting package to component`() {
        val intent = IntentUtils.createAppIntent(context, EXISTING_PACKAGE)

        assertEquals(EXISTING_PACKAGE, intent.getPackage())
        assertNotNull(intent.component)
        assertNull(intent.data)
    }

    @Test
    fun `test createAppIntent NonExistingPackageResolvedToBrowser`() {
        val intent = IntentUtils.createAppIntent(context, NOT_EXISTING_PACKAGE)

        assertTrue(intent.data!!.toString().startsWith("http"))
        assertNull(intent.component)
    }
}
