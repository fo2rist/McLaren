package com.github.fo2rist.mclaren.utils

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import com.github.fo2rist.mclaren.BuildConfig
import com.github.fo2rist.mclaren.testdata.DUMMY_URL
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.startsWith
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

private const val EXISTING_PACKAGE = BuildConfig.APPLICATION_ID

@RunWith(RobolectricTestRunner::class)
class IntentUtilsTest {

    private val context: Context by lazy { ApplicationProvider.getApplicationContext() }

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
        val intent: Intent = context.packageManager.getLaunchIntentForPackage(EXISTING_PACKAGE)
            ?: return fail("Package not resolved")
        val launched = IntentUtils.launchSafely(context, intent)

        assertTrue(launched)
        assertEquals(
                EXISTING_PACKAGE,
                shadowOf(ApplicationProvider.getApplicationContext() as Application).nextStartedActivity.component!!.packageName
        )
    }
}
