package com.github.fo2rist.mclaren.tests

import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.fo2rist.mclaren.pages.PreviewPage
import com.github.fo2rist.mclaren.testdata.FeedItems.TWITTER_GALLERY_ITEM
import com.github.fo2rist.mclaren.testutilities.ActivityTestBase
import com.github.fo2rist.mclaren.ui.previewscreen.PreviewActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ImagePreviewActivityTest : ActivityTestBase() {
    @get:Rule
    var rule = ActivityTestRule(PreviewActivity::class.java, false, false)
    private val page = PreviewPage()

    @Test
    fun testLoadsGallery() {
        rule.launchActivity(PreviewActivity.createFeedItemIntent(context, TWITTER_GALLERY_ITEM))


        page {
            toolbarImages{
                isGone()
            }

            galleryView {
                isDisplayed()
                swipeLeft()
            }
        }
    }
}
