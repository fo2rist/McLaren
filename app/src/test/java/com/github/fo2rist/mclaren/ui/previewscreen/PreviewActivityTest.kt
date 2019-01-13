package com.github.fo2rist.mclaren.ui.previewscreen

import android.content.Context
import android.content.Intent
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.testdata.FeedItems.MCLAREN_ARTICLE_ITEM
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.github.fo2rist.mclaren.testdata.FeedItems.VIDEO_ITEM
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.android.controller.ActivityController
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class PreviewActivityTest : BasePreviewActivityTest() {

    lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.application.applicationContext
    }

    @Test
    fun `test start for Url starts with default title not in fullscreen`() {
        val activity = startForIntent(
                PreviewActivity.createUrlIntent(context, "http://dummy.url")).get()

        assertFalse(activity.isFinishing)
        assertEquals(0, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(context.getString(R.string.app_name), activity.supportActionBar?.title)
    }

    @Test
    fun `test start for Image starts with default title in fullscreen`() {
        val activity = startForIntent(
                PreviewActivity.createFeedItemIntent(context, TWITTER_GALLERY_ITEM)).get()

        assertFalse(activity.isFinishing)
        assertEquals(FLAG_FULLSCREEN, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(context.getString(R.string.app_name), activity.supportActionBar?.title)
    }

    @Test
    fun `test start for Article starts with custom title`() {
        val activity = startForIntent(
                PreviewActivity.createFeedItemIntent(context, MCLAREN_ARTICLE_ITEM)).get()

        assertFalse(activity.isFinishing)
        assertEquals(0, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(MCLAREN_ARTICLE_ITEM.text, activity.supportActionBar?.title)
    }

    @Test
    fun `test start for Video finishes immediately`() {
        val activity = startForIntent(
                PreviewActivity.createFeedItemIntent(context, VIDEO_ITEM)).get()

        assertTrue(activity.isFinishing)
    }

    private fun startForIntent(intent: Intent): ActivityController<PreviewActivity> {
        return Robolectric.buildActivity(PreviewActivity::class.java, intent)
                .setup()

    }
}
