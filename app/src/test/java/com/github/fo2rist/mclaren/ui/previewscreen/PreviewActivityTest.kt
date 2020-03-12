package com.github.fo2rist.mclaren.ui.previewscreen

import android.content.Intent
import com.google.android.material.appbar.CollapsingToolbarLayout
import android.view.View
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import com.github.fo2rist.mclaren.R
import com.github.fo2rist.mclaren.mvp.PreviewContract
import com.github.fo2rist.mclaren.testdata.DUMMY_URL
import com.github.fo2rist.mclaren.testdata.FeedItems.ARTICLE_ITEM_WITH_LINKS
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.nhaarman.mockitokotlin2.spy
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class PreviewActivityTest {

    private val context by lazy { RuntimeEnvironment.application.applicationContext }

    private lateinit var activity: PreviewActivity
    private lateinit var presenterSpy: PreviewContract.Presenter

    @Test
    fun `test start for Url starts with default title not in fullscreen`() {
        startForIntent(
                PreviewActivity.createUrlIntent(context, DUMMY_URL))

        assertFalse(activity.isFinishing)
        assertEquals(0, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(context.getString(R.string.app_name), activity.supportActionBar?.title)

        activity.enterFullScreen()
        assertEquals(FLAG_FULLSCREEN, activity.window.attributes.flags and FLAG_FULLSCREEN)
    }

    @Test
    fun `test enterFullScreen changes fullscreen flags`() {
        startForIntent(
                PreviewActivity.createUrlIntent(context, DUMMY_URL))

        activity.enterFullScreen()
        assertEquals(FLAG_FULLSCREEN, activity.window.attributes.flags and FLAG_FULLSCREEN)
    }

    @Test
    fun `test setTitle changes actionbar title`() {
        startForIntent(
                PreviewActivity.createUrlIntent(context, DUMMY_URL))

        val testTitle = "TEST TITLE"
        activity.setTitle(testTitle)
        assertEquals(testTitle, activity.supportActionBar?.title)
    }


    @Test
    fun `test hideToolBar changes collapsing toolbar visibility`() {
        startForIntent(
                PreviewActivity.createUrlIntent(context, DUMMY_URL))

        activity.hideToolBar()
        assertEquals(View.GONE,
                activity.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout).visibility)
    }

    @Test
    fun `test start for Image starts with default title in fullscreen`() {
        startForIntent(
                PreviewActivity.createFeedItemIntent(context, TWITTER_GALLERY_ITEM))

        assertFalse(activity.isFinishing)
        assertEquals(FLAG_FULLSCREEN, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(context.getString(R.string.app_name), activity.supportActionBar?.title)
    }

    @Test
    fun `test start for Article starts with custom title`() {
        startForIntent(
                PreviewActivity.createFeedItemIntent(context, ARTICLE_ITEM_WITH_LINKS))

        assertFalse(activity.isFinishing)
        assertEquals(0, activity.window.attributes.flags and FLAG_FULLSCREEN)
        assertEquals(ARTICLE_ITEM_WITH_LINKS.text, activity.supportActionBar?.title)
    }

    private fun startForIntent(intent: Intent) {
        val activityController = Robolectric.buildActivity(PreviewActivity::class.java, intent)
                .setup()

        activity = activityController.get()
        presenterSpy = spy(activity.presenter)
        activity.presenter = presenterSpy
    }
}
